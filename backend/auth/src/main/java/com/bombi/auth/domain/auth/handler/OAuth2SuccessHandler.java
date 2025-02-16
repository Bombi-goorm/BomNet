package com.bombi.auth.domain.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bombi.auth.domain.auth.service.TokenService;
import com.bombi.auth.domain.auth.util.TokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private final TokenProvider tokenProvider;
	private final TokenService tokenService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		String accessToken = tokenProvider.generateAccessToken(authentication);
		String refreshToken = tokenProvider.generateRefreshToken();
		String refreshTokenString = tokenProvider.extractClaim(refreshToken);

		tokenService.save(authentication.getName(), refreshTokenString);

		addTokenInCookie(accessToken, "Authorization", response);
		addTokenInCookie(refreshToken, "refreshToken", response);

		response.sendRedirect("/home");
	}

	private void addTokenInCookie(String token, String cookieName, HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, token);

		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(3600);

		response.addCookie(cookie);
	}
}
