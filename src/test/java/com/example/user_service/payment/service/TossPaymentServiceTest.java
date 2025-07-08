package com.example.user_service.payment.service;

import static com.example.user_service.payment.dto.PaymentPrepareInfo.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.user_service.config.TossPaymentProperties;
import com.example.user_service.payment.domain.Coupon;
import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.dto.PaymentApproveInfo.PaymentApproveRequest;
import com.example.user_service.payment.dto.PaymentApproveInfo.PaymentApproveResponse;
import com.example.user_service.payment.repository.CouponRepository;
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
	private PointService pointService;
	@Mock
	private CouponService couponService;
	@Mock
	private CouponRepository couponRepository;

	// @Test
	// @DisplayName("유효한 결제 요청 시: 결제 정보 생성 후 Toss API 호출 및 응답 반환합니다.")
	// void requestPayment_success() {
	// 	// given
	// 	Long userId = 1L;
	// 	String paymentKey = "TOSS_TEST_KEY";
	// 	PaymentPrepareRequest request = createPaymentRequest(userId);
	// 	User user = new User("taewoo", "taewoo", "1234", 0);
	// 	Payment mockPayment = mock(Payment.class);
	// 	PaymentPrepareResponse mockResponse = createPaymentResponse(paymentKey);
	//
	// 	when(userRepository.findById(userId)).thenReturn(Optional.of(user));
	// 	when(tossPaymentProperties.getBaseUrl()).thenReturn("https://toss.payments.com");
	// 	when(tossApiClient.requestPayment(any(), any())).thenReturn(mockResponse);
	//
	// 	try (MockedStatic<PaymentConverter> converterMock = mockStatic(PaymentConverter.class)) {
	// 		converterMock.when(() -> PaymentConverter.createPaymentEntity(eq(request), anyString(), eq(user)))
	// 			.thenReturn(mockPayment);
	// 		converterMock.when(() -> PaymentConverter.createPropsResponse(eq(request), anyString(), anyString()))
	// 			.thenReturn(mockResponse);
	//
	// 		// when
	// 		PaymentPrepareResponse response = tossPaymentService.requestPayment(request);
	//
	// 		// then
	// 		assertNotNull(response);
	// 		assertEquals("TOSS_TEST_KEY", response.getPaymentKey());
	// 		assertEquals("ORDER_123", response.getOrderId());
	// 		assertEquals("SUCCESS", response.getStatus());
	//
	// 		verify(mockPayment).request();
	// 		verify(paymentRepository).save(mockPayment);
	// 		verify(tossApiClient).requestPayment(any(), any());
	// 	}
	// }

	@Test
	@DisplayName("결제 승인 성공 시: 결제 금액만큼 포인트가 적립된다.")
	void approvePayment_success_shouldChargePoint() {
		// given
		Long userId = 1L;
		Long amount = 5000L;
		String paymentKey = "TOSS_APPROVE_KEY";
		String orderId = "ORDER_456";
		User user = new User("taewoo", "taewoo@t.com", "pw", 0);
		Payment payment = mock(Payment.class);
		when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(payment);
		when(payment.getOrderId()).thenReturn(orderId);
		when(payment.getUser()).thenReturn(user);
		when(payment.getAmount()).thenReturn(amount);

		PaymentApproveRequest request = PaymentApproveRequest.builder()
			.paymentKey(paymentKey)
			.orderId(orderId)
			.amount(amount)
			.userId(userId)
			.build();

		try (MockedStatic<PaymentConverter> converterMock = mockStatic(PaymentConverter.class)) {
			PaymentApproveResponse mockResponse = PaymentApproveResponse.builder().build();
			converterMock.when(
					() -> PaymentConverter.toApproveResponse(eq(payment), eq(request), any(LocalDateTime.class)))
				.thenReturn(mockResponse);

			// when
			PaymentApproveResponse response = tossPaymentService.approvePayment(request);

			// then
			assertNotNull(response);
			verify(payment).approve();
			verify(paymentRepository).save(payment);
			verify(pointService).addPoint(userId, amount);
		}
	}

	@Test
	@DisplayName("쿠폰이 있을 때 20% 할인, 최대 5000원까지만 할인된다.")
	void approvePayment_withCoupon_appliesDiscount() {
		// given
		Long userId = 1L;
		Long originalAmount = 30000L;
		String paymentKey = "TOSS_APPROVE_KEY";
		String orderId = "ORDER_789";
		String couponCode = "DISCOUNT20";
		Coupon coupon = Coupon.builder()
			.id(1L)
			.code(couponCode)
			.discountRate(0.2)
			.maxDiscount(5000L)
			.expiredAt(LocalDateTime.now().plusDays(1))
			.enabled(true)
			.build();
		Payment payment = mock(Payment.class);
		when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(payment);
		when(payment.getId()).thenReturn(10L);
		when(couponRepository.findByCode(couponCode)).thenReturn(java.util.Optional.of(coupon));
		when(couponService.calculateDiscountAmount(coupon, originalAmount)).thenReturn(5000L);

		PaymentApproveRequest request = PaymentApproveRequest.builder()
			.paymentKey(paymentKey)
			.orderId(orderId)
			.amount(originalAmount)
			.userId(userId)
			.couponCode(couponCode)
			.build();

		// when
		PaymentApproveResponse response = tossPaymentService.approvePayment(request);

		// then
		assertThat(response.getAmount()).isEqualTo(25000L); // 30000 - 5000
		verify(couponService).calculateDiscountAmount(coupon, originalAmount);
		verify(couponService).useCoupon(userId, couponCode, 10L);
		verify(pointService).chargePoint(argThat(arg -> arg.getAmount().equals(25000L)));
	}

	@Test
	@DisplayName("쿠폰이 없을 때는 할인 없이 결제된다.")
	void approvePayment_withoutCoupon_noDiscount() {
		// given
		Long userId = 2L;
		Long originalAmount = 10000L;
		String paymentKey = "TOSS_APPROVE_KEY2";
		String orderId = "ORDER_790";
		Payment payment = mock(Payment.class);
		when(paymentRepository.findByPaymentKey(paymentKey)).thenReturn(payment);
		when(payment.getId()).thenReturn(20L);

		PaymentApproveRequest request = PaymentApproveRequest.builder()
			.paymentKey(paymentKey)
			.orderId(orderId)
			.amount(originalAmount)
			.userId(userId)
			.build();

		// when
		PaymentApproveResponse response = tossPaymentService.approvePayment(request);

		// then
		assertThat(response.getAmount()).isEqualTo(10000L);
		verify(pointService).chargePoint(argThat(arg -> arg.getAmount().equals(10000L)));
		verify(couponService, never()).calculateDiscountAmount(any(), anyLong());
		verify(couponService, never()).useCoupon(anyLong(), anyString(), anyLong());
	}

	private static PaymentPrepareResponse createPaymentResponse(String paymentKey) {
		PaymentPrepareResponse mockResponse = PaymentPrepareResponse.builder()
			.paymentKey(paymentKey)
			.paymentUrl("https://toss.payments.com/redirect")
			.orderId("ORDER_123")
			.expiredAt(LocalDateTime.now().plusMinutes(10))
			.paymentStatus("READY")
			.status("SUCCESS")
			.build();
		return mockResponse;
	}

	private static PaymentPrepareRequest createPaymentRequest(Long userId) {
		PaymentPrepareRequest request = PaymentPrepareRequest.builder()
			.userId(userId)
			.amount(10000)
			.paymentMethod("CARD")
			.orderId("ORDER_123")
			.successUrl("https://test.com/success")
			.failUrl("https://test.com/fail")
			.build();
		return request;
	}
}