package com.example.user_service.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class KakaoPaymentPrepareInfo {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoPaymentPrepareRequest {
        private Long userId;
        private Integer amount;
        private String paymentMethod;
        private String orderId;
        private String successUrl;
        private String failUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoPaymentPrepareResponse {
        private String paymentKey;
        private String paymentUrl;
        private String orderId;
        private LocalDateTime expiredAt;
        private String paymentStatus;
        private String status;
    }
} 