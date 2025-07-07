package com.example.user_service.payment.client;

import static com.example.user_service.payment.dto.PaymentPrepareInfo.*;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TossApiClient {
	private final WebClient webClient;

	public PaymentPrepareResponse requestPayment(PaymentPrepareRequest req, PaymentPrepareResponse props) {
		return webClient.post()
			.uri(props.getPaymentUrl() + "/payments")
			.header("Authorization", "Basic " + props.getPaymentKey())
			.bodyValue(req)
			.retrieve()
			.bodyToMono(PaymentPrepareResponse.class)
			.block();
	}
}