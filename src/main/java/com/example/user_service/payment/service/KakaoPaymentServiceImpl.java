package com.example.user_service.payment.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.user_service.exception.PaymentNotFoundException;
import com.example.user_service.payment.domain.Coupon;
import com.example.user_service.payment.domain.KakaoPaymentStatus;
import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.domain.PaymentMethod;
import com.example.user_service.payment.dto.KakaoPaymentApproveInfo.KakaoPaymentApproveRequest;
import com.example.user_service.payment.dto.KakaoPaymentApproveInfo.KakaoPaymentApproveResponse;
import com.example.user_service.payment.dto.KakaoPaymentCancelInfo.KakaoPaymentCancelRequest;
import com.example.user_service.payment.dto.KakaoPaymentCancelInfo.KakaoPaymentCancelResponse;
import com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.KakaoPaymentPrepareRequest;
import com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.KakaoPaymentPrepareResponse;
import com.example.user_service.payment.repository.CouponRepository;
import com.example.user_service.payment.repository.PaymentRepository;
import com.example.user_service.payment.util.PaymentConverter;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeRequest;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeResponse;
import com.example.user_service.point.service.PointService;
import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class KakaoPaymentServiceImpl implements KakaoPaymentService {
	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;
	private final PointService pointService;
	private final CouponService couponService;
	private final CouponRepository couponRepository;
	private final WebClient webClient;

	public KakaoPaymentServiceImpl(PaymentRepository paymentRepository,
								   UserRepository userRepository,
								   PointService pointService,
								   CouponService couponService,
								   CouponRepository couponRepository,
								   @Qualifier("kakaoPayWebClient") WebClient webClient) {
		this.paymentRepository = paymentRepository;
		this.userRepository = userRepository;
		this.pointService = pointService;
		this.couponService = couponService;
		this.couponRepository = couponRepository;
		this.webClient = webClient;
	}

	@Transactional
	@Override
	public KakaoPaymentPrepareResponse requestPayment(KakaoPaymentPrepareRequest request) {
		String paymentKey = "KAKAO-" + System.currentTimeMillis();
		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new PaymentNotFoundException("유저 정보 없음"));
		Payment payment = PaymentConverter.createPaymentEntity(
			request.getPaymentMethod() != null ? PaymentMethod.valueOf(request.getPaymentMethod()) : null,
			paymentKey, user);
		payment.request();
		paymentRepository.save(payment);
		return KakaoPaymentPrepareResponse.builder()
			.paymentKey(paymentKey)
			.paymentUrl("/mock/kakao/" + paymentKey)
			.orderId(request.getOrderId())
			.expiredAt(LocalDateTime.now().plusMinutes(15))
			.paymentStatus(KakaoPaymentStatus.READY.name())
			.status("READY")
			.build();
	}

	@Transactional
	@Override
	public KakaoPaymentApproveResponse approvePayment(KakaoPaymentApproveRequest request) {
		Payment payment = paymentRepository.findByPaymentKey(request.getPaymentKey());
		if (payment == null) {
			throw new PaymentNotFoundException("결제 정보 없음");
		}
		payment.approve();
		paymentRepository.save(payment);

		long originalAmount = request.getAmount();
		long discount = 0L;
		Coupon coupon = null;
		if (request.getCouponCode() != null && !request.getCouponCode().isEmpty()) {
			coupon = couponRepository.findByCode(request.getCouponCode()).orElse(null);
			if (coupon != null) {
				discount = couponService.calculateDiscountAmount(coupon, originalAmount);
				couponService.useCoupon(request.getUserId(), coupon.getCode(), payment.getId());
			}
		}
		long finalAmount = originalAmount - discount;
		if (finalAmount < 0)
			finalAmount = 0;

		PointChargeRequest pointRequest = PointChargeRequest.builder()
			.userId(request.getUserId())
			.amount(finalAmount)
			.paymentMethod(null)
			.build();
		PointChargeResponse pointResponse = pointService.chargePoint(pointRequest);

		return KakaoPaymentApproveResponse.builder()
			.paymentKey(request.getPaymentKey())
			.orderId(request.getOrderId())
			.amount(finalAmount)
			.paymentStatus(KakaoPaymentStatus.APPROVED.name())
			.approvedAt(LocalDateTime.now())
			.paymentMethod(request.getPaymentMethod())
			.couponCode(request.getCouponCode())
			.build();
	}

	@Transactional
	@Override
	public KakaoPaymentCancelResponse cancelPayment(KakaoPaymentCancelRequest request) {
		Payment payment = paymentRepository.findByPaymentKey(request.getPaymentKey());
		if (payment == null) {
			throw new PaymentNotFoundException("결제 정보 없음");
		}
		payment.cancel();
		paymentRepository.save(payment);
		return KakaoPaymentCancelResponse.builder()
			.paymentKey(request.getPaymentKey())
			.orderId(request.getOrderId())
			.cancelStatus(KakaoPaymentStatus.CANCELLED.name())
			.canceledAt(LocalDateTime.now())
			.build();
	}
} 