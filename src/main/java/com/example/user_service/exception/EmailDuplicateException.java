package com.example.user_service.exception;

public class EmailDuplicateException extends RuntimeException {
	public EmailDuplicateException(String message) {
		super(message);
	}
} 