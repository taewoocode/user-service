package com.example.user_service.payment.service;

import java.util.List;

import com.example.user_service.payment.domain.Coupon;
import com.example.user_service.payment.domain.CouponUsage;

public interface CouponService {
	List<Coupon> getAvailableCouponsForUser(Long userId);

	CouponUsage useCoupon(Long userId, String couponCode, Long paymentId);

	List<CouponUsage> getCouponUsagesByUser(Long userId);

	long calculateDiscountAmount(Coupon coupon, long originalAmount);
}