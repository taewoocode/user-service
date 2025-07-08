package com.example.user_service.point.domain;

import java.time.LocalDateTime;

import com.example.user_service.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "points")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long balance;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	private LocalDateTime updatedAt;

	public void charge(Long amount) {
		this.balance += amount;
		this.updatedAt = LocalDateTime.now();
	}

	public Point(User user, Long balance) {
		this.user = user;
		this.balance = balance;
		this.updatedAt = LocalDateTime.now();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
}
