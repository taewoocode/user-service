package com.example.user_service.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserProfileInfo {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserProfileRequest {
		private String keyword;
		private String sortBy;

		// "asc", "desc"
		private String direction;

		// 페이지 번호
		private int page;

		// 페이지 크기
		private int size;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserProfileResponse {
		private String name;
		private long viewCount;
		private LocalDateTime createdAt;
	}
} 