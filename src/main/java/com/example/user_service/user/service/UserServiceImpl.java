package com.example.user_service.user.service;

import static com.example.user_service.user.dto.UserProfileDetailInfo.*;
import static com.example.user_service.user.dto.UserProfileInfo.*;
import static com.example.user_service.user.dto.UserRegisterInfo.*;
import static com.example.user_service.user.service.UserConverter.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	/**
	 * 회원가입 요청
	 * @param request
	 * @return
	 */
	@Override
	@Transactional
	public UserRegisterResponse saveUser(UserRegisterRequest request) {
		User user = createUserEntity(request);
		User saved = userRepository.save(user);
		return toRegisterResponse(saved);
	}

	/**
	 * 유저 목록 반환
	 * @param request
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<UserProfileResponse> searchProfiles(UserProfileRequest request, Pageable pageable) {
		return userRepository.searchProfiles(request, pageable);
	}

	/**
	 * User 프로필 상세 조회
	 * @param request
	 * @return
	 */
	@Override
	public UserProfileDetailResponse getUserProfileDetail(UserProfileDetailRequest request) {
		return userRepository.findAndIncreaseViewCount(request.getUserId());
	}
}
