package com.example.user_service.payment.util;

import static com.example.user_service.payment.dto.PaymentApproveInfo.*;
import static com.example.user_service.payment.dto.PaymentCancelInfo.*;
import static com.example.user_service.payment.dto.PaymentPrepareInfo.*;

import java.time.LocalDateTime;

import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.domain.PaymentMethod;
import com.example.user_service.user.domain.User;

public class PaymentConverter {
	public static Payment createPaymentEntity(PaymentPrepareRequest request, String paymentKey,
		User user) {
		return Payment.builder()
			.paymentKey(paymentKey)
			.orderId(request.getOrderId())
			.amount(request.getAmount() != null ? request.getAmount().longValue() : null)
			.user(user)
			.paymentMethod(
				request.getPaymentMethod() != null ? PaymentMethod.valueOf(request.getPaymentMethod()) : null)
			.build();
	}

	public static PaymentPrepareResponse createPropsResponse(
		PaymentPrepareRequest request, String paymentKey, String baseUrl) {
		return PaymentPrepareResponse.builder()
			.paymentKey(paymentKey)
			.orderId(request.getOrderId())
			.paymentUrl(baseUrl + "/pay/" + paymentKey)
			.build();
	}

	public static PaymentApproveResponse toApproveResponse(Payment payment,
		PaymentApproveRequest request, LocalDateTime now) {
		return PaymentApproveResponse.builder()
			.paymentKey(payment.getPaymentKey())
			.orderId(payment.getOrderId())
			.amount(payment.getAmount())
			.paymentStatus(payment.getPaymentStatus())
			.approvedAt(now)
			.paymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null)
			.couponCode(request.getCouponCode())
			.build();
	}

	public static PaymentCancelResponse toCancelResponse(Payment payment,
		PaymentCancelRequest request, LocalDateTime now) {
		return PaymentCancelResponse.builder()
			.paymentKey(payment.getPaymentKey())
			.orderId(payment.getOrderId())
			.cancelStatus(payment.getPaymentStatus())
			.canceledAt(now)
			.build();
	}
}