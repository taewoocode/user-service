package com.example.user_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	@Bean(name = "tossWebClient")
	public WebClient tossWebClient() {
		return WebClient.builder().build();
	}

	@Bean(name = "kakaoPayWebClient")
	public WebClient kakaoPayWebClient(@Autowired KakaoPayProperties kakaoPayProperties) {
		return WebClient.builder()
			.baseUrl(kakaoPayProperties.getBaseUrl())
			.defaultHeader("Authorization", "KakaoAK " + kakaoPayProperties.getAdminKey())
			.build();
	}
} 