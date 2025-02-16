package com.bombi.auth.domain.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bombi.auth.domain.auth.exception.TokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenExceptionFilter extends OncePerRequestFilter {

	private final AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (TokenException e) {
			log.info("error: {}", e.getMessage());
			// response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
			authenticationEntryPoint.commence(request, response,
				new InsufficientAuthenticationException(e.getMessage()));
		}
	}
}
