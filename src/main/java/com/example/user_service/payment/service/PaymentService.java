package com.example.user_service.payment.service;

import com.example.user_service.payment.dto.PaymentPrepareInfo;
import com.example.user_service.payment.dto.PaymentApproveInfo;
import com.example.user_service.payment.dto.PaymentCancelInfo;

public interface PaymentService {
	/**
	 * 결제 준비를 처리합니다.
	 * @param request 결제 준비 요청 정보
	 * @return 결제 준비 응답 정보
	 */
	PaymentPrepareInfo.PaymentPrepareResponse requestPayment(PaymentPrepareInfo.PaymentPrepareRequest request);

	/**
	 * 결제 승인을 처리합니다.
	 * @param request 결제 승인 요청 정보
	 * @return 결제 승인 응답 정보
	 */
	PaymentApproveInfo.PaymentApproveResponse approvePayment(PaymentApproveInfo.PaymentApproveRequest request);

	/**
	 * 결제 취소를 처리합니다.
	 * @param request 결제 취소 요청 정보
	 * @return 결제 취소 응답 정보
	 */
	PaymentCancelInfo.PaymentCancelResponse cancelPayment(PaymentCancelInfo.PaymentCancelRequest request);
}
