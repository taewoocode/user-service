package com.example.user_service.payment.service;

import static com.example.user_service.payment.dto.PaymentApproveInfo.*;
import static com.example.user_service.payment.dto.PaymentCancelInfo.*;
import static com.example.user_service.payment.dto.PaymentPrepareInfo.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_service.config.TossPaymentProperties;
import com.example.user_service.exception.PaymentNotFoundException;
import com.example.user_service.payment.client.TossApiClient;
import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.repository.PaymentRepository;
import com.example.user_service.payment.util.PaymentConverter;
import com.example.user_service.point.service.PointService;
import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TossPaymentService implements PaymentService {
	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;
	private final TossPaymentProperties tossPaymentProperties;
	private final PointService pointService;
	private final TossApiClient tossApiClient;

	/**
	 *
	 * @param request 결제 준비 요청 정보
	 * @return
	 */
	@Transactional
	@Override
	public PaymentPrepareResponse requestPayment(PaymentPrepareRequest request) {
		String paymentKey = "TOSS_" + UUID.randomUUID();

		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new PaymentNotFoundException("결제 정보 없음"));
		Payment payment = PaymentConverter.createPaymentEntity(request, paymentKey, user);

		payment.request();
		paymentRepository.save(payment);

		String baseUrl = tossPaymentProperties.getBaseUrl();
		PaymentPrepareResponse props = PaymentConverter.createPropsResponse(request, paymentKey, baseUrl);

		return tossApiClient.requestPayment(request, props);
	}

	/**
	 * @param request 결제 승인 요청 정보
	 * @return
	 */
	@Transactional
	@Override
	public PaymentApproveResponse approvePayment(PaymentApproveRequest request) {
		Payment payment = paymentRepository.findByPaymentKey(request.getPaymentKey());
		if (payment == null) {
			throw new PaymentNotFoundException("결제 정보 없음");
		}
		if (!payment.getOrderId().equals(request.getOrderId())) {
			throw new IllegalArgumentException("orderId 불일치");
		}
		payment.approve();
		paymentRepository.save(payment);
		pointService.addPoint(payment.getUser().getId(), payment.getAmount());
		return PaymentConverter.toApproveResponse(payment, request, LocalDateTime.now());
	}

	/**
	 * @param request 결제 취소 요청 정보
	 * @return
	 */
	@Transactional
	@Override
	public PaymentCancelResponse cancelPayment(PaymentCancelRequest request) {
		Payment payment = paymentRepository.findByPaymentKey(request.getPaymentKey());
		if (payment == null) {
			throw new PaymentNotFoundException("결제 정보 없음");
		}
		payment.cancel();
		paymentRepository.save(payment);
		return PaymentConverter.toCancelResponse(payment, request, LocalDateTime.now());
	}
} 