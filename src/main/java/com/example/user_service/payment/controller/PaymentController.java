package com.example.user_service.payment.controller;

import com.example.user_service.payment.dto.PaymentPrepareInfo.PaymentPrepareRequest;
import com.example.user_service.payment.dto.PaymentPrepareInfo.PaymentPrepareResponse;
import com.example.user_service.payment.dto.PaymentApproveInfo.PaymentApproveRequest;
import com.example.user_service.payment.dto.PaymentApproveInfo.PaymentApproveResponse;
import com.example.user_service.payment.dto.PaymentCancelInfo.PaymentCancelRequest;
import com.example.user_service.payment.dto.PaymentCancelInfo.PaymentCancelResponse;
import com.example.user_service.payment.service.TossPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final TossPaymentService tossPaymentService;

    @PostMapping("/prepare")
    public PaymentPrepareResponse preparePayment(@RequestBody PaymentPrepareRequest request) {
        return tossPaymentService.requestPayment(request);
    }

    @PostMapping("/approve")
    public PaymentApproveResponse approvePayment(@RequestBody PaymentApproveRequest request) {
        return tossPaymentService.approvePayment(request);
    }

    @PostMapping("/cancel")
    public PaymentCancelResponse cancelPayment(@RequestBody PaymentCancelRequest request) {
        return tossPaymentService.cancelPayment(request);
    }
}
