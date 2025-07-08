package com.example.user_service.payment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_service.config.TossPaymentProperties;
import com.example.user_service.exception.PaymentNotFoundException;
import com.example.user_service.payment.domain.Coupon;
import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.domain.TossPaymentStatus;
import com.example.user_service.payment.dto.TossPaymentApproveInfo;
import com.example.user_service.payment.dto.TossPaymentCancelInfo;
import com.example.user_service.payment.dto.TossPaymentPrepareInfo;
import com.example.user_service.payment.repository.CouponRepository;
import com.example.user_service.payment.repository.PaymentRepository;
import com.example.user_service.payment.util.PaymentConverter;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeRequest;
import com.example.user_service.point.dto.PointChargeInfo.PointChargeResponse;
import com.example.user_service.point.repository.PointRepository;
import com.example.user_service.point.service.PointService;
import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TossPaymentService implements PaymentService {
	private final PaymentRepository paymentRepository;
	private final PointRepository pointRepository;
	private final UserRepository userRepository;
	private final TossPaymentProperties tossPaymentProperties;
	private final PointService pointService;
	private final CouponService couponService;
	private final CouponRepository couponRepository;

	@Transactional
	public TossPaymentPrepareInfo.TossPaymentPrepareResponse requestPayment(
		TossPaymentPrepareInfo.TossPaymentPrepareRequest request) {
		String paymentKey = "TOSS-" + System.currentTimeMillis();
		String baseUrl = tossPaymentProperties.getClientKey(); // 실제로는 baseUrl, clientKey 등 활용
		String paymentUrl = baseUrl + "/mock/" + paymentKey;

		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new PaymentNotFoundException("유저 정보 없음"));
		Payment payment = PaymentConverter.createPaymentEntity(null, paymentKey, user);
		payment.request();
		paymentRepository.save(payment);
		return TossPaymentPrepareInfo.TossPaymentPrepareResponse.builder()
			.paymentKey(paymentKey)
			.paymentUrl(paymentUrl)
			.orderId(request.getOrderId())
			.expiredAt(LocalDateTime.now().plusMinutes(15))
			.paymentStatus(TossPaymentStatus.REQUESTED.name())
			.status("READY")
			.build();
	}

	@Transactional
	public TossPaymentApproveInfo.TossPaymentApproveResponse approvePayment(
		TossPaymentApproveInfo.PaymentApproveRequest request) {
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
			coupon = couponRepository.findByCode(request.getCouponCode())
				.orElse(null);
			if (coupon != null) {
				discount = couponService.calculateDiscountAmount(coupon, originalAmount);
				couponService.useCoupon(request.getUserId(), coupon.getCode(), payment.getId());
			}
		}
		long finalAmount = originalAmount - discount;
		if (finalAmount < 0)
			finalAmount = 0;

		// 포인트 적립
		PointChargeRequest pointRequest = PointChargeRequest.builder()
			.userId(request.getUserId())
			.amount(finalAmount)
			.paymentMethod(null) // 필요시 변환
			.build();
		PointChargeResponse pointResponse = pointService.chargePoint(pointRequest);

		return TossPaymentApproveInfo.TossPaymentApproveResponse.builder()
			.paymentKey(request.getPaymentKey())
			.orderId(request.getOrderId())
			.amount(finalAmount)
			.paymentStatus("APPROVED")
			.approvedAt(LocalDateTime.now())
			.paymentMethod(request.getPaymentMethod())
			.couponCode(request.getCouponCode())
			.build();
	}

	@Transactional
	public TossPaymentCancelInfo.TossPaymentCancelResponse cancelPayment(
		TossPaymentCancelInfo.TossPaymentCancelRequest request) {
		Payment payment = paymentRepository.findByPaymentKey(request.getPaymentKey());
		if (payment == null) {
			throw new PaymentNotFoundException("결제 정보 없음");
		}
		payment.cancel();
		paymentRepository.save(payment);
		return TossPaymentCancelInfo.TossPaymentCancelResponse.builder()
			.paymentKey(request.getPaymentKey())
			.orderId(request.getOrderId())
			.cancelStatus("CANCELLED")
			.canceledAt(LocalDateTime.now())
			.build();
	}
} 