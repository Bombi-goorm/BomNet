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

		// Header에 쿠키 설정
		String cookieHeader = generateTokenCookie(accessToken, refreshToken);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.COOKIE, cookieHeader);

		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<?> response = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity, String.class);

		log.info("renew response :: {}", response);

		if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			log.error("JwtGenerator::renewToken - token renewal failed");
			throw new InvalidTokenException("token renewal failed");
		}

		List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
		if (cookies == null || cookies.isEmpty()) {
			throw new IllegalArgumentException("renew failed: no cookies");
		}

		// access_token 쿠키 문자열에서 JWT 부분만 추출
		String fullCookie = cookies.stream()
				.filter(cookie -> cookie.startsWith("access_token"))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("renew failed: no access_token cookie"));

		log.info("renewed access_token fullCookie :: {}", fullCookie);

		// "access_token=eyJ...; Path=/..." 형식 → "="으로 split 후 첫 번째 값 제외, ";"로 split해서 토큰만 추출
		String tokenPart = fullCookie.split(";")[0]; // access_token=eyJ...
		String tokenValue = tokenPart.split("=")[1]; // eyJ...

		return tokenValue;
	}

	private static String generateTokenCookie(String accessToken, String refreshToken) {
		Cookie accessTokenCookie = new Cookie("access_token", accessToken);
		Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);

		return accessTokenCookie.getName() + "=" + accessTokenCookie.getValue()
			+ "; " + refreshTokenCookie.getName() + "=" + refreshTokenCookie.getValue();
	}
}
