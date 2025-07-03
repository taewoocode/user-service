package com.example.user_service.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.user.dto.UserRegisterInfo;
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
	public ResponseEntity<UserRegisterInfo.UserRegisterResponse> registerUser(
		@RequestBody UserRegisterInfo.UserRegisterRequest request) {
		UserRegisterInfo.UserRegisterResponse response = userService.saveUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
