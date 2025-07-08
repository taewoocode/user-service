package com.example.user_service.point.service;

import com.example.user_service.point.dto.PointChargeInfo.PointChargeRequest;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeResponse;

public interface PointService {
	PointChargeResponse chargePoint(PointChargeRequest request);

	void addPoint(Long userId, Long amount);
}