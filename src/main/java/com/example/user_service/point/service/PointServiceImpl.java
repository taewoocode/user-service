package com.example.user_service.point.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_service.point.domain.PointHistory;
import com.example.user_service.point.domain.PointHistoryType;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeRequest;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeResponse;
import com.example.user_service.point.repository.PointHistoryRepository;
import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
	private final PointHistoryRepository pointHistoryRepository;
	private final UserRepository userRepository;

	// 결제 승인 결과를 받아 포인트 적립만 책임
	@Transactional
	@Override
	public PointChargeResponse chargePoint(PointChargeRequest request) {
		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new UsernameNotFoundException("유저 없음"));

		PointHistory history = PointConverter.toPointHistory(
			request,
			user,
			PointHistoryType.CHARGE,
			LocalDateTime.now()
		);
		pointHistoryRepository.save(history);

		return PointChargeResponse.builder()
			.userId(user.getId())
			.chargedAmount(request.getAmount())
			.newBalance(null)
			.paymentKey(null)
			.orderId(null)
			.paymentUrl(null)
			.build();
	}

	@Override
	public void addPoint(Long userId, Long amount) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UsernameNotFoundException("유저 없음"));
		PointHistory history = PointHistory.builder()
			.user(user)
			.amount(amount != null ? amount.intValue() : null)
			.type(PointHistoryType.CHARGE)
			.createdAt(LocalDateTime.now())
			.build();
		pointHistoryRepository.save(history);
	}
}
