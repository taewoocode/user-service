package com.example.user_service.user.service;

import com.example.user_service.user.domain.User;
import com.example.user_service.user.dto.UserRegisterInfo;

public class UserConverter {
	public static User createUserEntity(UserRegisterInfo.UserRegisterRequest request) {
		return User.builder()
			.name(request.getName())
			.email(request.getEmail())
			.password(request.getPassword())
			.build();
	}

	public static UserRegisterInfo.UserRegisterResponse toRegisterResponse(User user) {
		return UserRegisterInfo.UserRegisterResponse.builder()
			.id(user.getId())
			.name(user.getName())
			.email(user.getEmail())
			.createdAt(user.getCreatedAt())
			.build();
	}
}
