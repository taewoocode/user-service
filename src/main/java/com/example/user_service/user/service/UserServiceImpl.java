package com.example.user_service.user.service;

import static com.example.user_service.user.service.UserConverter.*;

import org.springframework.stereotype.Service;

import com.example.user_service.user.domain.User;
import com.example.user_service.user.dto.UserRegisterInfo;
import com.example.user_service.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public UserRegisterInfo.UserRegisterResponse saveUser(UserRegisterInfo.UserRegisterRequest request) {
		User user = createUserEntity(request);
		User saved = userRepository.save(user);
		return toRegisterResponse(saved);
	}
}
