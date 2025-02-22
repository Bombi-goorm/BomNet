package com.bombi.core.common.exception;

public class NewsFailedException extends RuntimeException {
	public NewsFailedException(String message) {
		super(message);
	}

	public NewsFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
