package com.example.user_service.point.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.point.dto.PointChargeInfo.PointChargeRequest;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeResponse;
import com.example.user_service.point.service.PointService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {
	private final PointService pointService;

	@PostMapping("/charge")
	public PointChargeResponse chargePoint(@RequestBody PointChargeRequest request) {
		return pointService.chargePoint(request);
	}
}
