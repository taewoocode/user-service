package com.example.user_service.user.service;

import static com.example.user_service.user.dto.UserRegisterInfo.*;
import static com.example.user_service.user.service.UserConverter.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.user_service.user.domain.User;
import com.example.user_service.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	@DisplayName("회원가입이 성공한다.")
	void register_user_success() {
		// given
		UserRegisterRequest testUser = UserRegisterRequest.builder()
			.name("현우")
			.email("test@email.com")
			.password("1234")
			.build();

		log.info("테스트용 회원가입 요청 ={}", testUser);

		User testUserEntity = createUserEntity(testUser);
		given(userRepository.save(any(User.class))).willReturn(testUserEntity);

		// when
		UserRegisterResponse savedUser = userService.saveUser(testUser);

		log.info("회원가입 결과 ={}", savedUser);

		// then
		assertThat(savedUser.getEmail()).isEqualTo("test@email.com");
		verify(userRepository).save(any(User.class));

		log.info("userRepository.save 호출 및 결과 검증 완료 ={}", savedUser.getEmail());
	}
}