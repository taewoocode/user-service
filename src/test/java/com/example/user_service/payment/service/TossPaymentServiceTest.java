package com.example.user_service.payment.service;

import static com.example.user_service.payment.dto.PaymentPrepareInfo.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.user_service.config.TossPaymentProperties;
import com.example.user_service.payment.client.TossApiClient;
import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.repository.PaymentRepository;
import com.example.user_service.payment.util.PaymentConverter;
import com.example.user_service.point.service.PointService;
import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
class TossPaymentServiceTest {

	@InjectMocks
	private TossPaymentService tossPaymentService;

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TossPaymentProperties tossPaymentProperties;

	@Mock
	private TossApiClient tossApiClient;

	@Mock
	private PointService pointService;

	@Test
	@DisplayName("유효한 결제 요청 시: 결제 정보 생성 후 Toss API 호출 및 응답 반환합니다.")
	void requestPayment_success() {
		// given
		Long userId = 1L;
		String paymentKey = "TOSS_TEST_KEY";

		PaymentPrepareRequest request = PaymentPrepareRequest.builder()
			.userId(userId)
			.amount(10000)
			.paymentMethod("CARD")
			.orderId("ORDER_123")
			.successUrl("https://test.com/success")
			.failUrl("https://test.com/fail")
			.build();

		User user = new User("taewoo", "taewoo", "1234", 0);
		Payment mockPayment = mock(Payment.class);

		PaymentPrepareResponse mockResponse = PaymentPrepareResponse.builder()
			.paymentKey(paymentKey)
			.paymentUrl("https://toss.payments.com/redirect")
			.orderId("ORDER_123")
			.expiredAt(LocalDateTime.now().plusMinutes(10))
			.paymentStatus("READY")
			.status("SUCCESS")
			.build();

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		when(tossPaymentProperties.getBaseUrl()).thenReturn("https://toss.payments.com");

		when(tossApiClient.requestPayment(any(), any())).thenReturn(mockResponse);

		// mock: 정적 메서드
		try (MockedStatic<PaymentConverter> converterMock = mockStatic(PaymentConverter.class)) {
			converterMock.when(() -> PaymentConverter.toPayment(eq(request), anyString(), eq(user)))
				.thenReturn(mockPayment);
			converterMock.when(() -> PaymentConverter.createPropsResponse(eq(request), anyString(), anyString()))
				.thenReturn(mockResponse);

			// when
			PaymentPrepareResponse response = tossPaymentService.requestPayment(request);

			// then
			assertNotNull(response);
			assertEquals("TOSS_TEST_KEY", response.getPaymentKey());
			assertEquals("ORDER_123", response.getOrderId());
			assertEquals("SUCCESS", response.getStatus());

			verify(mockPayment).request();
			verify(paymentRepository).save(mockPayment);
			verify(tossApiClient).requestPayment(any(), any());
		}
	}
}