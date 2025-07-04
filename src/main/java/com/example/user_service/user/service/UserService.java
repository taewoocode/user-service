package com.example.user_service.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.user_service.user.dto.UserProfileInfo;
import com.example.user_service.user.dto.UserRegisterInfo;

public interface UserService {

	UserRegisterInfo.UserRegisterResponse saveUser(UserRegisterInfo.UserRegisterRequest request);

	Page<UserProfileInfo.UserProfileResponse> searchProfiles(UserProfileInfo.UserProfileRequest request,
		Pageable pageable);
}
