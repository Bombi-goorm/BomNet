package com.bombi.auth.domain.auth.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

		tokenService.save(authentication.getName(), refreshToken);

		addTokenInCookie(accessToken, "Authorization", response);
		addTokenInCookie(refreshToken, "refreshToken", response);

		response.sendRedirect("/home");
	}

	private void addTokenInCookie(String token, String cookieName, HttpServletResponse response) {
		String encodedToken =
			Base64.getUrlEncoder().withoutPadding().encodeToString(token.getBytes(StandardCharsets.UTF_8));

		Cookie tokenCookie = new Cookie(cookieName, encodedToken);
		tokenCookie.setHttpOnly(true);
		tokenCookie.setSecure(true);
		tokenCookie.setPath("/");
		tokenCookie.setMaxAge(3600);

		response.addCookie(tokenCookie);
	}
}
