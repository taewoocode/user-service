package com.example.user_service.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class KakaoPaymentApproveInfo {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KakaoPaymentApproveRequest {
        private String paymentKey;
        private String orderId;
        private Long amount;
        private Long userId;
        private String paymentMethod;
        private String couponCode;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KakaoPaymentApproveResponse {
        private String paymentKey;
        private String orderId;
        private Long amount;
        private String paymentStatus;
        private LocalDateTime approvedAt;
        private String paymentMethod;
        private String couponCode;
    }
} 