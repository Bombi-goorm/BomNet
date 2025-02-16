package com.bombi.auth.domain.auth.filter;

import java.io.IOException;
import java.util.Arrays;
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

	private static final List<String> PERMITTED_URLS = List.of("/login", "/login.html", "/default-ui.css");
	private static final String AUTHORIZATION_COOKIE = "Authorization";
	private static final String REFRESH_COOKIE = "refreshToken";

	private final TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String requestURI = request.getRequestURI();

		if (PERMITTED_URLS.contains(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = extractTokenByCookieName(request, AUTHORIZATION_COOKIE);

		if (tokenProvider.validateToken(accessToken)) {
			setAuthentication(accessToken);
		} else {
			String refreshToken = extractTokenByCookieName(request, REFRESH_COOKIE);

			if(tokenProvider.validateRefreshToken(accessToken, refreshToken)) {
				String reissueAccessToken = tokenProvider.reissueAccessToken(accessToken);
				setAuthentication(reissueAccessToken);
				addTokenInCookie(response, reissueAccessToken);
			} else {
				throw new TokenException("리프레시 토큰 만료.Refresh Token Expired");
			}
		}

		filterChain.doFilter(request, response);
	}

	private String extractTokenByCookieName(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies)
			.filter(cookie -> cookieName.equals(cookie.getName()))
			.findFirst();

		return refreshTokenCookie.map(Cookie::getValue).orElse(null);
	}

	private void setAuthentication(String accessToken) {
		Authentication authentication = tokenProvider.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void addTokenInCookie(HttpServletResponse response, String reissueAccessToken) {
		Cookie cookie = new Cookie("Authorization", reissueAccessToken);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(3600); // 1시간 유지

		response.addCookie(cookie);
	}

}
