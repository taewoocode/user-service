package com.example.user_service.payment.util;

import static com.example.user_service.payment.dto.TossPaymentApproveInfo.*;
import static com.example.user_service.payment.dto.TossPaymentCancelInfo.*;
import static com.example.user_service.payment.dto.TossPaymentPrepareInfo.*;
import static com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.*;
import static com.example.user_service.payment.dto.KakaoPaymentApproveInfo.*;
import static com.example.user_service.payment.dto.KakaoPaymentCancelInfo.*;

import java.time.LocalDateTime;

import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.domain.PaymentMethod;
import com.example.user_service.user.domain.User;

public class PaymentConverter {
	public static Payment createPaymentEntity(PaymentMethod paymentMethod, String paymentKey, User user) {
		return Payment.builder()
			.paymentKey(paymentKey)
			.paymentMethod(paymentMethod)
			.user(user)
			.build();
	}

	public static TossPaymentPrepareResponse createPropsResponse(
		TossPaymentPrepareRequest request, String paymentKey, String baseUrl) {
		return TossPaymentPrepareResponse.builder()
			.paymentKey(paymentKey)
			.orderId(request.getOrderId())
			.paymentUrl(baseUrl + "/pay/" + paymentKey)
			.build();
	}

	public static TossPaymentApproveResponse toApproveResponse(Payment payment,
		PaymentApproveRequest request, LocalDateTime now) {
		return TossPaymentApproveResponse.builder()
			.paymentKey(payment.getPaymentKey())
			.orderId(payment.getOrderId())
			.amount(payment.getAmount())
			.paymentStatus(payment.getPaymentStatus())
			.approvedAt(now)
			.paymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null)
			.couponCode(request.getCouponCode())
			.build();
	}

	public static TossPaymentCancelResponse toCancelResponse(Payment payment,
		TossPaymentCancelRequest request, LocalDateTime now) {
		return TossPaymentCancelResponse.builder()
			.paymentKey(payment.getPaymentKey())
			.orderId(payment.getOrderId())
			.cancelStatus(payment.getPaymentStatus())
			.canceledAt(now)
			.build();
	}

	// Toss 변환 메서드
	public static Payment createTossPaymentEntity(PaymentMethod paymentMethod, String paymentKey, User user) {
		return Payment.builder()
			.paymentKey(paymentKey)
			.paymentMethod(paymentMethod)
			.user(user)
			.build();
	}

	public static TossPaymentPrepareResponse createTossPropsResponse(
		TossPaymentPrepareRequest request, String paymentKey, String baseUrl) {
		return TossPaymentPrepareResponse.builder()
			.paymentKey(paymentKey)
			.orderId(request.getOrderId())
			.paymentUrl(baseUrl + "/pay/" + paymentKey)
			.build();
	}

	public static TossPaymentApproveResponse toTossApproveResponse(Payment payment,
		PaymentApproveRequest request, LocalDateTime now) {
		return TossPaymentApproveResponse.builder()
			.paymentKey(payment.getPaymentKey())
			.orderId(payment.getOrderId())
			.amount(payment.getAmount())
			.paymentStatus(payment.getPaymentStatus())
			.approvedAt(now)
			.paymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null)
			.couponCode(request.getCouponCode())
			.build();
	}

	public static TossPaymentCancelResponse toTossCancelResponse(Payment payment,
		TossPaymentCancelRequest request, LocalDateTime now) {
		return TossPaymentCancelResponse.builder()
			.paymentKey(payment.getPaymentKey())
			.orderId(payment.getOrderId())
			.cancelStatus(payment.getPaymentStatus())
			.canceledAt(now)
			.build();
	}

	// KakaoPay 변환 메서드
	public static Payment createKakaoPaymentEntity(PaymentMethod paymentMethod, String paymentKey, User user) {
		return Payment.builder()
			.paymentKey(paymentKey)
			.paymentMethod(paymentMethod)
			.user(user)
			.build();
	}

	public static KakaoPaymentPrepareResponse createKakaoPropsResponse(
		KakaoPaymentPrepareRequest request, String paymentKey, String baseUrl) {
		return KakaoPaymentPrepareResponse.builder()
			.paymentKey(paymentKey)
			.orderId(request.getOrderId())
			.paymentUrl(baseUrl + "/pay/" + paymentKey)
			.expiredAt(LocalDateTime.now().plusMinutes(15))
			.paymentStatus("READY")
			.status("READY")
			.build();
	}

	public static KakaoPaymentApproveResponse toKakaoApproveResponse(Payment payment,
		KakaoPaymentApproveRequest request, LocalDateTime now) {
		return KakaoPaymentApproveResponse.builder()
			.paymentKey(payment.getPaymentKey())
			.orderId(payment.getOrderId())
			.amount(payment.getAmount())
			.paymentStatus(payment.getPaymentStatus())
			.approvedAt(now)
			.paymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null)
			.couponCode(request.getCouponCode())
			.build();
	}

	public static KakaoPaymentCancelResponse toKakaoCancelResponse(Payment payment,
		KakaoPaymentCancelRequest request, LocalDateTime now) {
		return KakaoPaymentCancelResponse.builder()
			.paymentKey(payment.getPaymentKey())
			.orderId(payment.getOrderId())
			.cancelStatus(payment.getPaymentStatus())
			.canceledAt(now)
			.build();
	}
}