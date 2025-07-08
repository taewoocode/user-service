package com.example.user_service.payment.service;

import com.example.user_service.payment.dto.TossPaymentApproveInfo;
import com.example.user_service.payment.dto.TossPaymentCancelInfo;
import com.example.user_service.payment.dto.TossPaymentPrepareInfo;

public interface PaymentService {
	/**
	 * 결제 준비를 처리합니다.
	 * @param request 결제 준비 요청 정보
	 * @return 결제 준비 응답 정보
	 */
	TossPaymentPrepareInfo.TossPaymentPrepareResponse requestPayment(
		TossPaymentPrepareInfo.TossPaymentPrepareRequest request);

	/**
	 * 결제 승인을 처리합니다.
	 * @param request 결제 승인 요청 정보
	 * @return 결제 승인 응답 정보
	 */
	TossPaymentApproveInfo.TossPaymentApproveResponse approvePayment(
		TossPaymentApproveInfo.PaymentApproveRequest request);

	/**
	 * 결제 취소를 처리합니다.
	 * @param request 결제 취소 요청 정보
	 * @return 결제 취소 응답 정보
	 */
	TossPaymentCancelInfo.TossPaymentCancelResponse cancelPayment(
		TossPaymentCancelInfo.TossPaymentCancelRequest request);
}
