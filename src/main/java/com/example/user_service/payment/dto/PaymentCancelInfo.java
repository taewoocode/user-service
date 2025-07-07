package com.example.user_service.payment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PaymentCancelInfo {
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PaymentCancelRequest {
		private String paymentKey;
		private String cancelReason;
		private Long cancelAmount;
		private String orderId;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PaymentCancelResponse {
		private String paymentKey;
		private String orderId;
		private String cancelStatus;
		private LocalDateTime canceledAt;
	}
} 