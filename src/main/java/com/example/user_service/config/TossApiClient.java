package com.example.user_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TossApiClient {
	private final WebClient webClient;

	public TossApiClient(@Qualifier("tossWebClient") WebClient webClient) {
		this.webClient = webClient;
	}
}