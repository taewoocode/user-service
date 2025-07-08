package com.example.user_service.payment.domain;

// src/main/java/com/example/user_service/payment/domain/KakaoPaymentStatus.java
public enum KakaoPaymentStatus implements PaymentStatusType {
	READY("READY", "카카오 결제 준비"),
	APPROVED("APPROVED", "카카오 결제 승인"),
	CANCELLED("CANCELLED", "카카오 결제 취소");

	private final String code;
	private final String description;

	KakaoPaymentStatus(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}