package com.example.user_service.payment.service;

import com.example.user_service.payment.domain.KakaoPaymentStatus;
import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.domain.PaymentMethod;
import com.example.user_service.payment.dto.KakaoPaymentApproveInfo.KakaoPaymentApproveRequest;
import com.example.user_service.payment.dto.KakaoPaymentApproveInfo.KakaoPaymentApproveResponse;
import com.example.user_service.payment.dto.KakaoPaymentCancelInfo.KakaoPaymentCancelRequest;
import com.example.user_service.payment.dto.KakaoPaymentCancelInfo.KakaoPaymentCancelResponse;
import com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.KakaoPaymentPrepareRequest;
import com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.KakaoPaymentPrepareResponse;
import com.example.user_service.payment.repository.CouponRepository;
import com.example.user_service.payment.repository.PaymentRepository;
import com.example.user_service.point.service.PointService;
import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.lang.reflect.Field;

public class KakaoPaymentServiceImplTest {
    @Mock private PaymentRepository paymentRepository;
    @Mock private UserRepository userRepository;
    @Mock private PointService pointService;
    @Mock private CouponService couponService;
    @Mock private CouponRepository couponRepository;

    @InjectMocks
    private KakaoPaymentServiceImpl kakaoPaymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("카카오 결제 준비가 정상 동작한다")
    void testRequestPayment() throws Exception {
        User user = User.builder()
                .name("테스트")
                .email("test@test.com")
                .password("pw")
                .viewCount(0L)
                .build();
        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, 1L);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        KakaoPaymentPrepareRequest request = KakaoPaymentPrepareRequest.builder()
                .userId(1L)
                .amount(10000)
                .paymentMethod(PaymentMethod.KAKAO.name())
                .orderId("ORDER-1")
                .successUrl("http://success")
                .failUrl("http://fail")
                .build();

        KakaoPaymentPrepareResponse response = kakaoPaymentService.requestPayment(request);
        assertThat(response.getPaymentKey()).contains("KAKAO-");
        assertThat(response.getOrderId()).isEqualTo("ORDER-1");
        assertThat(response.getPaymentStatus()).isEqualTo(KakaoPaymentStatus.READY.name());
    }

    @Test
    @DisplayName("카카오 결제 승인 로직이 정상 동작한다")
    void testApprovePayment() {
        Payment payment = Payment.builder()
                .paymentKey("KAKAO-1234")
                .paymentMethod(PaymentMethod.KAKAO)
                .build();
        when(paymentRepository.findByPaymentKey("KAKAO-1234")).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        KakaoPaymentApproveRequest request = KakaoPaymentApproveRequest.builder()
                .paymentKey("KAKAO-1234")
                .orderId("ORDER-1")
                .amount(10000L)
                .userId(1L)
                .paymentMethod(PaymentMethod.KAKAO.name())
                .build();

        KakaoPaymentApproveResponse response = kakaoPaymentService.approvePayment(request);
        assertThat(response.getPaymentKey()).isEqualTo("KAKAO-1234");
        assertThat(response.getOrderId()).isEqualTo("ORDER-1");
        assertThat(response.getAmount()).isEqualTo(10000L);
        assertThat(response.getPaymentStatus()).isEqualTo(KakaoPaymentStatus.APPROVED.name());
    }

    @Test
    @DisplayName("카카오 결제 취소 로직이 정상 동작한다")
    void testCancelPayment() {
        Payment payment = Payment.builder()
                .paymentKey("KAKAO-1234")
                .paymentMethod(PaymentMethod.KAKAO)
                .build();
        when(paymentRepository.findByPaymentKey("KAKAO-1234")).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        KakaoPaymentCancelRequest request = KakaoPaymentCancelRequest.builder()
                .paymentKey("KAKAO-1234")
                .orderId("ORDER-1")
                .cancelReason("테스트취소")
                .cancelAmount(10000L)
                .build();

        KakaoPaymentCancelResponse response = kakaoPaymentService.cancelPayment(request);
        assertThat(response.getPaymentKey()).isEqualTo("KAKAO-1234");
        assertThat(response.getOrderId()).isEqualTo("ORDER-1");
        assertThat(response.getCancelStatus()).isEqualTo(KakaoPaymentStatus.CANCELLED.name());
    }
} 