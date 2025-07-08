package com.example.user_service.point.dto;

import com.example.user_service.payment.domain.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PointChargeInfo {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PointChargeResponse {
		private Long userId;
		private Long chargedAmount;
		private Long newBalance;
		private String paymentKey;
		private String orderId;
		private String paymentUrl;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PointChargeRequest {
		private Long userId;
		private Long amount;
		private PaymentMethod paymentMethod;
	}

}
