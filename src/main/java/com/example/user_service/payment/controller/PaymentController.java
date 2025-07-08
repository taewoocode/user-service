package com.example.user_service.payment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.payment.domain.Coupon;
import com.example.user_service.payment.dto.TossPaymentApproveInfo.PaymentApproveRequest;
import com.example.user_service.payment.dto.TossPaymentApproveInfo.TossPaymentApproveResponse;
import com.example.user_service.payment.dto.TossPaymentCancelInfo.TossPaymentCancelRequest;
import com.example.user_service.payment.dto.TossPaymentCancelInfo.TossPaymentCancelResponse;
import com.example.user_service.payment.dto.TossPaymentPrepareInfo.TossPaymentPrepareRequest;
import com.example.user_service.payment.dto.TossPaymentPrepareInfo.TossPaymentPrepareResponse;
import com.example.user_service.payment.service.CouponService;
import com.example.user_service.payment.service.TossPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final TossPaymentService tossPaymentService;
	private final CouponService couponService;

	/**
	 * 사용가능한 쿠폰리스트 조회
	 * @param userId
	 * @return
	 */
	@GetMapping("/coupons")
	public List<Coupon> getAvailableCoupons(@RequestParam Long userId) {
		return couponService.getAvailableCouponsForUser(userId);
	}

	@PostMapping("/prepare")
	public TossPaymentPrepareResponse preparePayment(@RequestBody TossPaymentPrepareRequest request) {
		return tossPaymentService.requestPayment(request);
	}

	@PostMapping("/approve")
	public TossPaymentApproveResponse approvePayment(@RequestBody PaymentApproveRequest request) {
		return tossPaymentService.approvePayment(request);
	}

	@PostMapping("/cancel")
	public TossPaymentCancelResponse cancelPayment(@RequestBody TossPaymentCancelRequest request) {
		return tossPaymentService.cancelPayment(request);
	}
}
