package com.example.user_service.point.service;

import java.time.LocalDateTime;

import com.example.user_service.point.domain.PointHistory;
import com.example.user_service.point.domain.PointHistoryType;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeRequest;
import com.example.user_service.user.domain.User;

public class PointConverter {

	public static PointHistory toPointHistory(PointChargeRequest request, User user, PointHistoryType type,
		LocalDateTime createdAt) {
		return PointHistory.builder()
			.user(user)
			.amount(request.getAmount().intValue())
			.type(type)
			.createdAt(createdAt)
			.build();
	}
} 