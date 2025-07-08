package com.example.user_service.payment.repository;

import com.example.user_service.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByPaymentKey(String paymentKey);
} 