package com.example.user_service.payment.repository;

import com.example.user_service.payment.domain.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {
    List<CouponUsage> findByUserId(Long userId);
    boolean existsByUserIdAndCouponIdAndUsedTrue(Long userId, Long couponId);
} 