package com.example.user_service.payment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_service.payment.domain.Coupon;
import com.example.user_service.payment.domain.CouponUsage;
import com.example.user_service.payment.domain.Payment;
import com.example.user_service.payment.repository.CouponRepository;
import com.example.user_service.payment.repository.CouponUsageRepository;
import com.example.user_service.payment.repository.PaymentRepository;
import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
	private final CouponRepository couponRepository;
	private final CouponUsageRepository couponUsageRepository;
	private final UserRepository userRepository;
	private final PaymentRepository paymentRepository;

	@Override
	public List<Coupon> getAvailableCouponsForUser(Long userId) {
		List<CouponUsage> usages = couponUsageRepository.findByUserId(userId);
		List<Long> usedCouponIds = usages.stream()
			.filter(CouponUsage::isUsed)
			.map(u -> u.getCoupon().getId())
			.collect(Collectors.toList());
		return couponRepository.findAll().stream()
			.filter(c -> c.isEnabled() && c.getExpiredAt().isAfter(LocalDateTime.now()) && !usedCouponIds.contains(
				c.getId()))
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public CouponUsage useCoupon(Long userId, String couponCode, Long paymentId) {
		Coupon coupon = couponRepository.findByCode(couponCode)
			.orElseThrow(() -> new IllegalArgumentException("쿠폰 코드가 유효하지 않습니다."));
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유저 없음"));
		Payment payment = paymentRepository.findById(paymentId)
			.orElseThrow(() -> new IllegalArgumentException("결제 정보 없음"));
		// 이미 사용한 쿠폰인지 체크
		if (couponUsageRepository.existsByUserIdAndCouponIdAndUsedTrue(userId, coupon.getId())) {
			throw new IllegalStateException("이미 사용한 쿠폰입니다.");
		}
		return couponUsageRepository.save(createCouponResponse(coupon, user, payment));
	}

	private static CouponUsage createCouponResponse(Coupon coupon, User user, Payment payment) {
		CouponUsage usage = CouponUsage.builder()
			.coupon(coupon)
			.user(user)
			.payment(payment)
			.used(true)
			.usedAt(LocalDateTime.now())
			.build();
		return usage;
	}

	@Override
	public List<CouponUsage> getCouponUsagesByUser(Long userId) {
		return couponUsageRepository.findByUserId(userId);
	}

	@Override
	public long calculateDiscountAmount(Coupon coupon, long originalAmount) {
		if (coupon == null || !coupon.isEnabled() || coupon.getExpiredAt().isBefore(LocalDateTime.now())) {
			return 0L;
		}
		long discount = (long)(originalAmount * coupon.getDiscountRate());
		return Math.min(discount, coupon.getMaxDiscount());
	}
} 