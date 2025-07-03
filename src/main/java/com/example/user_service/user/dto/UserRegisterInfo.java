package com.example.user_service.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRegisterInfo {

	/**
	 * 회원가입 DTO
	 */

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserRegisterRequest {
		private String name;
		private String password;
		private String email;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserRegisterResponse {
		private Long id;
		private String name;
		private String email;
		private LocalDateTime createdAt;
	}
}
