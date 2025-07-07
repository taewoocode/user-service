package com.example.user_service.point.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_service.payment.dto.PaymentRequest;
import com.example.user_service.payment.dto.PaymentResponse;
import com.example.user_service.payment.service.PaymentService;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeRequest;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
	private final PaymentService paymentService;

	//paymentService -> 포인트 정보 전달
	@Transactional
	@Override
	public PointChargeResponse chargePoint(PointChargeRequest request) {
		PaymentRequest paymentRequest = PointConverter.toPaymentRequest(request);
		PaymentResponse paymentResponse = paymentService.requestPayment(paymentRequest);
		return PointConverter.toPointChargeResponse(request, paymentResponse);
	}
}
