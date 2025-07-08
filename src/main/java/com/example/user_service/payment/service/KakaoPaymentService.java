package com.example.user_service.payment.service;

import com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.KakaoPaymentPrepareRequest;
import com.example.user_service.payment.dto.KakaoPaymentPrepareInfo.KakaoPaymentPrepareResponse;
import com.example.user_service.payment.dto.KakaoPaymentApproveInfo.KakaoPaymentApproveRequest;
import com.example.user_service.payment.dto.KakaoPaymentApproveInfo.KakaoPaymentApproveResponse;
import com.example.user_service.payment.dto.KakaoPaymentCancelInfo.KakaoPaymentCancelRequest;
import com.example.user_service.payment.dto.KakaoPaymentCancelInfo.KakaoPaymentCancelResponse;

public interface KakaoPaymentService {
    KakaoPaymentPrepareResponse requestPayment(KakaoPaymentPrepareRequest request);
    KakaoPaymentApproveResponse approvePayment(KakaoPaymentApproveRequest request);
    KakaoPaymentCancelResponse cancelPayment(KakaoPaymentCancelRequest request);
} 