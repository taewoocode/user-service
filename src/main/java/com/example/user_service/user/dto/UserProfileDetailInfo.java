package com.example.user_service.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserProfileDetailInfo {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserProfileDetailRequest {
		private Long userId;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserProfileDetailResponse {
		private Long userId;
		private String name;
		private long viewCount;
		private LocalDateTime localDateTime;
	}
} 