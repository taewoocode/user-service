package com.example.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmailDuplicateException.class)
	public ResponseEntity<String> handleEmailDuplicateException(EmailDuplicateException e) {
		log.error("이메일 중복 오류: {}", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
} 