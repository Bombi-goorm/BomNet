package com.bombi.core.common.exception;

public class SoilCharacterApiException extends RuntimeException {
	public SoilCharacterApiException(String message) {
		super(message);
	}

	public SoilCharacterApiException(String message, Throwable cause) {
		super(message, cause);
	}
}
