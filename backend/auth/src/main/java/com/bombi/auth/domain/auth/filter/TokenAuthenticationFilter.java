package com.bombi.auth.domain.auth.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bombi.auth.domain.auth.exception.TokenException;
import com.bombi.auth.domain.auth.util.TokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String requestURI = request.getRequestURI();

		List<String> permittedUrls = List.of("/login", "/default-ui.css");

		if (permittedUrls.contains(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = extractAccessToken(request);

		if (tokenProvider.validateToken(accessToken)) {
			Authentication authentication = tokenProvider.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			String refreshToken = extractRefreshToken(request);
			if(tokenProvider.validateRefreshToken(accessToken, refreshToken)) {
				String reissueAccessToken = tokenProvider.reissueAccessToken(accessToken);
				Authentication authentication = tokenProvider.getAuthentication(reissueAccessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);

				addTokenInCookie(response, reissueAccessToken);
			} else {
				throw new TokenException("리프레시 토큰 만료.Refresh Token Expired");
			}
		}

		filterChain.doFilter(request, response);
	}

	private void addTokenInCookie(HttpServletResponse response, String reissueAccessToken) {
		String encodedAccessToken =
			Base64.getUrlEncoder().withoutPadding().encodeToString(reissueAccessToken.getBytes(StandardCharsets.UTF_8));

		Cookie newAccessTokenCookie = new Cookie("Authorization", encodedAccessToken);
		newAccessTokenCookie.setHttpOnly(true);
		newAccessTokenCookie.setSecure(true);
		newAccessTokenCookie.setPath("/");
		newAccessTokenCookie.setMaxAge(3600); // 1시간 유지

		response.addCookie(newAccessTokenCookie);
	}

	private String extractAccessToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
			.filter(cookie -> "Authorization".equals(cookie.getName()))
			.findFirst();

		return accessTokenCookie.map(cookie -> {
			String value = cookie.getValue();
			return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
		}).orElse(null);
	}

	private String extractRefreshToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies)
			.filter(cookie -> "refreshToken".equals(cookie.getName()))
			.findFirst();

		return refreshTokenCookie.map(cookie -> {
			String value = cookie.getValue();
			return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
		}).orElse(null);
	}

}
