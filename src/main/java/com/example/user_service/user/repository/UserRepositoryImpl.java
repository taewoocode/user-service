package com.example.user_service.user.repository;

import static com.example.user_service.user.dto.UserProfileInfo.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import com.example.user_service.user.domain.QUser;
import com.example.user_service.user.dto.UserProfileDetailInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<UserProfileResponse> searchProfiles(UserProfileRequest request,
		Pageable pageable) {
		QUser user = QUser.user;

		BooleanBuilder builder = new BooleanBuilder();
		if (StringUtils.hasText(request.getKeyword())) {
			builder.and(user.name.containsIgnoreCase(request.getKeyword()));
		}

		OrderSpecifier<?> orderSpecifier = getOrderSpecifier(request.getSortBy(), request.getDirection(), user);

		List<UserProfileResponse> content = queryFactory
			.selectFrom(user)
			.where(builder)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch()
			.stream()
			.map(u -> UserProfileResponse.builder()
				.name(u.getName())
				.viewCount(u.getViewCount())
				.createdAt(u.getCreatedAt())
				.build())
			.collect(Collectors.toList());

		long total = queryFactory.selectFrom(user).where(builder).fetchCount();

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public UserProfileDetailInfo.UserProfileDetailResponse findAndIncreaseViewCount(Long userId) {
		QUser user = QUser.user;
		long updateRows = queryFactory.update(user)
			.set(user.viewCount, user.viewCount.add(1))
			.where(user.id.eq(userId))
			.execute();

		if (updateRows == 0) {
			throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
		}

		return queryFactory
			.select(user)
			.where(user.id.eq(userId))
			.fetch()
			.stream()
			.findFirst()
			.map(u -> UserProfileDetailInfo.UserProfileDetailResponse.builder()
				.userId(u.getId())
				.name(u.getName())
				.viewCount(u.getViewCount())
				.createdAt(u.getCreatedAt())
				.build()).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

	}

	private OrderSpecifier<?> getOrderSpecifier(String sortBy, String direction, QUser user) {
		Order order = "desc".equalsIgnoreCase(direction) ? Order.DESC : Order.ASC;
		PathBuilder<?> entityPath = new PathBuilder<>(QUser.class, "user");
		if ("viewCount".equalsIgnoreCase(sortBy)) {
			return new OrderSpecifier<>(order, user.viewCount);
		} else if ("createdAt".equalsIgnoreCase(sortBy)) {
			return new OrderSpecifier<>(order, user.createdAt);
		} else {
			return new OrderSpecifier<>(order, user.name);
		}
	}
}