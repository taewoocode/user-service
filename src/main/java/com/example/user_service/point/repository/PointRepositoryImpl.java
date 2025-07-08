package com.example.user_service.point.repository;

import static com.example.user_service.point.domain.QPoint.*;
import static jakarta.persistence.LockModeType.*;

import org.springframework.stereotype.Repository;

import com.example.user_service.point.domain.Point;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepositoryCustom {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	@Override
	public Point findByUserIdWithLock(Long userId) {
		return queryFactory.selectFrom(point)
			.where(point.user.id.eq(userId))
			.setLockMode(PESSIMISTIC_WRITE)
			.fetchOne();
	}
} 