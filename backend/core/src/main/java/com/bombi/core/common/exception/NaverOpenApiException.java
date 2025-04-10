package com.bombi.core.common.exception;

import org.springframework.web.client.HttpStatusCodeException;

public class NaverOpenApiException extends RuntimeException {

	private final String category;

	public NaverOpenApiException(String message) {
		super(message);
		this.category = "NONE";
	}

	public NaverOpenApiException(String category, String message) {
		super(message);
		this.category = category;
	}

	public NaverOpenApiException(String category, String message, HttpStatusCodeException e) {
		super("Naver " + category + " OPEN API Error - " + message, e);
		this.category = category;
	}

	public NaverOpenApiException(String category, String message, Exception e) {
		super("Naver " + category + " OPEN API Error - " + message, e);
		this.category = category;
	}
}
