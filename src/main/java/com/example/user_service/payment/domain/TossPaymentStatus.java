package com.example.user_service.payment.domain;

// src/main/java/com/example/user_service/payment/domain/TossPaymentStatus.java
public enum TossPaymentStatus implements PaymentStatusType {
	REQUESTED("REQUESTED", "토스 결제 요청"),
	SUCCESS("SUCCESS", "토스 결제 성공"),
	FAIL("FAIL", "토스 결제 실패");

	private final String code;
	private final String description;

	TossPaymentStatus(String code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
