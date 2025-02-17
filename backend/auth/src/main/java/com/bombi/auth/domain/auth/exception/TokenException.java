package com.bombi.auth.domain.auth.exception;

public class TokenException extends RuntimeException {

	private String message;

	public TokenException(String message) {
		super(message);
	}
}
