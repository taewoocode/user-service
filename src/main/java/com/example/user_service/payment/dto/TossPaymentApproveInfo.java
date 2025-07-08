package com.example.user_service.payment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TossPaymentApproveInfo {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class PaymentApproveRequest {
		private String paymentKey;
		private String orderId;
		private Long amount;
		private Long userId;
		private String paymentMethod;
		private String couponCode;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class TossPaymentApproveResponse {
		private String paymentKey;
		private String orderId;
		private Long amount;
		private String paymentStatus;
		private LocalDateTime approvedAt;
		private String paymentMethod;
		private String couponCode;
	}
}