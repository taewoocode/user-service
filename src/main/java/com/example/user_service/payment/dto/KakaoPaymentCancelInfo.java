package com.example.user_service.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class KakaoPaymentCancelInfo {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoPaymentCancelRequest {
        private String paymentKey;
        private String cancelReason;
        private Long cancelAmount;
        private String orderId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoPaymentCancelResponse {
        private String paymentKey;
        private String orderId;
        private String cancelStatus;
        private LocalDateTime canceledAt;
    }
} 