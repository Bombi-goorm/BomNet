package com.bombi.core.infrastructure.security.authentication;


import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bombi.core.common.exception.InvalidTokenException;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtGenerator {

	public static final String TOKEN_RENEW_REQUEST_URL = "http://localhost:8180/member/renew";

	private final RestTemplate restTemplate;

	/**
	 * 인증 서버로 갱신 요청
	 */
	public String renewToken(String accessToken, String refreshToken) {
		if(refreshToken == null) {
			log.error("JwtGenerator::renewToken - refreshToken is null");
			throw new InvalidTokenException("Refresh token is null");
		}

		// request header에 accessToken과 refreshToken 넣기
		HttpEntity<Object> requestEntity = createRenewRequestHeader(accessToken, refreshToken);

		// AUTH 서버로 요청 보내기
		ResponseEntity<String> response = restTemplate.exchange(
			TOKEN_RENEW_REQUEST_URL,
			HttpMethod.POST,
			requestEntity,
			String.class);

		if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			log.error("JwtGenerator::renewToken - token renewal failed");
			throw new InvalidTokenException("token renewal failed");
		}

		// accessToken 추출
		List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);

		if (cookies == null || cookies.isEmpty()) {
			throw new InvalidTokenException("renew failed");
		}

		return extractNewAccessTokenFromCookie(cookies);
	}

	private HttpEntity<Object> createRenewRequestHeader(String accessToken, String refreshToken) {
		String cookieHeader = generateTokenCookie(accessToken, refreshToken);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.COOKIE, cookieHeader);

		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		return requestEntity;
	}

	private String generateTokenCookie(String accessToken, String refreshToken) {
		if (accessToken == null) {
			return "refresh_token=" + refreshToken;
		}
		return "access_token=" + accessToken + "; refresh_token=" + refreshToken;
	}

	private String extractNewAccessTokenFromCookie(List<String> cookies) {
		return cookies.stream()
			.filter(cookie -> cookie.startsWith("access_token="))
			.map(cookie -> cookie.split(";")[0].split("=")[1])
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("renew failed"));
	}
}
