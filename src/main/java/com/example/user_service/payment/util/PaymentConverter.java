package com.example.user_service.payment.util;

import static com.example.user_service.payment.dto.TossPaymentApproveInfo.*;
import static com.example.user_service.payment.dto.TossPaymentCancelInfo.*;
import static com.example.user_service.payment.dto.TossPaymentPrepareInfo.*;

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
}