package com.example.user_service.config;

import static com.example.user_service.payment.dto.TossPaymentPrepareInfo.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TossApiClient {
	private final WebClient webClient;

	public TossApiClient(@Qualifier("tossWebClient") WebClient webClient) {
		this.webClient = webClient;
	}

	public TossPaymentPrepareResponse requestPayment(TossPaymentPrepareRequest req, TossPaymentPrepareResponse props) {
		return webClient.post()
			.uri(props.getPaymentUrl() + "/payments")
			.header("Authorization", "Basic " + props.getPaymentKey())
			.bodyValue(req)
			.retrieve()
			.bodyToMono(TossPaymentPrepareResponse.class)
			.block();
	}
}