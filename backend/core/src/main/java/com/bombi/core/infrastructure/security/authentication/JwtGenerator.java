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

	private final RestTemplate restTemplate;

	/**
	 * 인증 서버로 갱신 요청
	 */
	public String renewToken(String accessToken, String refreshToken) {
		String requestUrl = "http://localhost:8180/member/renew";

		// request header에 accessToken과 refreshToken 넣기
		String cookieHeader = generateTokenCookie(accessToken, refreshToken);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.COOKIE, cookieHeader);

		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);


			// AUTH 서버로 요청 보내기
			ResponseEntity<?> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity,
					String.class);

		System.out.println(response);


		if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			log.error("JwtGenerator::renewToken - token renewal failed");
			throw new InvalidTokenException("token renewal failed");
		}

		// accessToken 추출
		List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);

		if (cookies == null || cookies.isEmpty()) {
			throw new IllegalArgumentException("renew failed");
		}

		return cookies.stream()
			.filter(cookie -> cookie.startsWith("access_token"))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("renew failed"));
	}

	private static String generateTokenCookie(String accessToken, String refreshToken) {
		Cookie accessTokenCookie = new Cookie("access_token", accessToken);
		Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);

		return accessTokenCookie.getName() + "=" + accessTokenCookie.getValue()
			+ "; " + refreshTokenCookie.getName() + "=" + refreshTokenCookie.getValue();
	}
}
