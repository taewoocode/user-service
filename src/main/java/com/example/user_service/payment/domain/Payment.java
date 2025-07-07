package com.example.user_service.payment.domain;

import java.time.LocalDateTime;

import com.example.user_service.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String paymentKey;

	@Column(nullable = false)
	private String orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private Long amount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TossPaymentStatus paymentStatus;

	@Column(nullable = false)
	private LocalDateTime requestedAt;

	private LocalDateTime approvedAt;

	private String providerOrderId;

}
