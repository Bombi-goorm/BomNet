package com.bombi.auth.domain.auth.util;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bombi.auth.domain.auth.exception.TokenException;
import com.bombi.auth.domain.auth.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {

	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 1L;
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 1L;
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private final Key key;
	private final TokenService tokenService;

	public TokenProvider(@Value("${jwt.secret}") String secretKey, TokenService tokenService) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.tokenService = tokenService;
	}

	public String generateAccessToken(Authentication authentication) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining());

		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim("role", authorities)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String generateRefreshToken() {
		String randomString = generateRandomString();

		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);

		return randomString + Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateToken(String token) {
		if (!StringUtils.hasText(token)) {
			return false;
		}

		Claims claims = parseClaimsFromToken(token);
		return claims.getExpiration().after(new Date());
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaimsFromToken(accessToken);
		List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

		User principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
	}

	public boolean validateRefreshToken(String accessToken, String refreshToken) {
		if (StringUtils.hasText(refreshToken) && validateToken(refreshToken)) {

			Claims claims = parseClaimsFromToken(accessToken);
			String username = claims.getSubject();

			return tokenService.validateToken(username, refreshToken);
		}

		return false;
	}

	public String reissueAccessToken(String accessToken) {
		Claims claims = parseClaimsFromToken(accessToken);
		String username = claims.getSubject();
		String authorities = claims.get("role", String.class);

		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

		return Jwts.builder()
			.setSubject(username)
			.claim("role", authorities)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	private String generateRandomString() {
		SecureRandom secureRandom = new SecureRandom();

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			int randomIndex = secureRandom.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			stringBuilder.append(randomChar);
		}

		return stringBuilder.toString();
	}

	private Claims parseClaimsFromToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build()
				.parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			log.info("토큰 만료");
			return e.getClaims();
		} catch (SecurityException e) {
			throw new TokenException("invalid token");
		}
	}

	private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
		return Collections.singletonList(new SimpleGrantedAuthority(claims.get("role").toString()));
	}
}
