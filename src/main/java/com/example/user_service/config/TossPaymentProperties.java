package com.example.user_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TossPaymentProperties {

	@Value("${toss.payments.client-key}")
	private String clientKey;

	@Value("${toss.payments.secret-key}")
	private String secretKey;

	@Value("${toss.payments.security-key}")
	private String securityKey;
}
