package com.example.user_service.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.user_service.user.dto.UserProfileDetailInfo;
import com.example.user_service.user.dto.UserProfileInfo;

public interface UserRepositoryCustom {
	Page<UserProfileInfo.UserProfileResponse> searchProfiles(UserProfileInfo.UserProfileRequest request,
		Pageable pageable);

	UserProfileDetailInfo.UserProfileDetailResponse findAndIncreaseViewCount(Long userId);
}