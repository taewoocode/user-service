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

	@ExceptionHandler({PaymentNotFoundException.class, PointNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleDomainNotFound(RuntimeException e) {
		log.error("도메인 NotFound: {}", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("NOT_FOUND", e.getMessage()));
	}

	@ExceptionHandler(AlreadyProcessedException.class)
	public ResponseEntity<ErrorResponse> handleAlreadyProcessed(AlreadyProcessedException e) {
		log.error("이미 처리된 요청: {}", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("ALREADY_PROCESSED", e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("서버 오류: {}", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("INTERNAL_ERROR", e.getMessage()));
	}

	static class ErrorResponse {
		private final String code;
		private final String message;
		public ErrorResponse(String code, String message) {
			this.code = code;
			this.message = message;
		}
		public String getCode() { return code; }
		public String getMessage() { return message; }
	}
} 