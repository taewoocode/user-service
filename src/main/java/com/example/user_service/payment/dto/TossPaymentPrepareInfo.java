package com.example.user_service.payment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TossPaymentPrepareInfo {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TossPaymentPrepareRequest {
		private Long userId;
		private Integer amount;
		private String paymentMethod;
		private String orderId;
		private String successUrl;
		private String failUrl;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TossPaymentPrepareResponse {
		private String paymentKey;
		private String paymentUrl;
		private String orderId;
		private LocalDateTime expiredAt;
		private String paymentStatus;
		private String status;
	}
} 