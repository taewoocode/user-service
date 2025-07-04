package com.example.user_service.user.controller;

import static com.example.user_service.user.dto.UserProfileInfo.*;
import static com.example.user_service.user.dto.UserRegisterInfo.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	/**
	 * 회원가입
	 * @param request 회원가입 요청 DTO
	 * @return 회원가입 결과 DTO
	 */
	@PostMapping
	public ResponseEntity<UserRegisterResponse> registerUser(
		@RequestBody UserRegisterRequest request) {
		UserRegisterResponse response = userService.saveUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * 회원 프로필 목록 조회
	 */
	@GetMapping("/profiles")
	public Page<UserProfileResponse> getUserProfiles(
		@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "name") String sortBy,
		@RequestParam(defaultValue = "asc") String direction,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size) {
		UserProfileRequest request = UserProfileRequest.builder()
			.keyword(keyword)
			.sortBy(sortBy)
			.direction(direction)
			.page(page)
			.size(size)
			.build();
		Pageable pageable = PageRequest.of(page, size);
		return userService.searchProfiles(request, pageable);
	}
}
