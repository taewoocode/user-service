package com.example.user_service.payment.controller;

import com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.KakaoPaymentPrepareRequest;
import com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.KakaoPaymentPrepareResponse;
import com.example.user_service.payment.dto.KakaoPaymentApproveInfo.KakaoPaymentApproveRequest;
import com.example.user_service.payment.dto.KakaoPaymentApproveInfo.KakaoPaymentApproveResponse;
import com.example.user_service.payment.dto.KakaoPaymentCancelInfo.KakaoPaymentCancelRequest;
import com.example.user_service.payment.dto.KakaoPaymentCancelInfo.KakaoPaymentCancelResponse;
import com.example.user_service.payment.service.KakaoPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kakao-payments")
@RequiredArgsConstructor
public class KakaoPaymentController {
    private final KakaoPaymentService kakaoPaymentService;

    @PostMapping("/prepare")
    public KakaoPaymentPrepareResponse prepare(@RequestBody KakaoPaymentPrepareRequest request) {
        return kakaoPaymentService.requestPayment(request);
    }

    @PostMapping("/approve")
    public KakaoPaymentApproveResponse approve(@RequestBody KakaoPaymentApproveRequest request) {
        return kakaoPaymentService.approvePayment(request);
    }

    @PostMapping("/cancel")
    public KakaoPaymentCancelResponse cancel(@RequestBody KakaoPaymentCancelRequest request) {
        return kakaoPaymentService.cancelPayment(request);
    }
} 