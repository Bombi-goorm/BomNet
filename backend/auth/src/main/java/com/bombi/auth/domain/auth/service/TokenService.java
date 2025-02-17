package com.bombi.auth.domain.auth.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private static final long TOKEN_DURATION = 1000 * 60 * 60L * 24 * 7;

	private final RedisTemplate<String, String> redisTemplate;

	public void save(String name, String refreshTokenString) {
		redisTemplate.opsForValue().set(name, refreshTokenString, Duration.ofMillis(TOKEN_DURATION));
	}

	public boolean validateToken(String username, String refreshToken) {
		String savedTokenString = redisTemplate.opsForValue().get(username);
		return refreshToken.equals(savedTokenString);
	}
}
