package com.example.user_service.aop;

import com.example.user_service.user.dto.UserRegisterInfo;
import com.example.user_service.user.repository.UserRepository;
import com.example.user_service.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserValidationAspect {
    private final UserRepository userRepository;

    @Before("execution(* com.example.user_service.user.service.UserService.saveUser(..)) && args(request,..)")
    public void checkEmailDuplicate(JoinPoint joinPoint, UserRegisterInfo.UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailDuplicateException("이미 사용 중인 이메일입니다.");
        }
    }
} 