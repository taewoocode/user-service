package com.example.user_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kakao.pay")
public class KakaoPayProperties {
	private String adminKey;
	private String secretKey;
	private String clientId;
	private String clientSecret;
	private String baseUrl;
	private String successUrl;
	private String failUrl;
} 