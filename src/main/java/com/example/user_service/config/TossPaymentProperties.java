package com.example.user_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "toss.payments")
public class TossPaymentProperties {
	private String clientKey;
	private String secretKey;
	private String securityKey;
	private String baseUrl;
	private String successUrl;
	private String failUrl;
}
