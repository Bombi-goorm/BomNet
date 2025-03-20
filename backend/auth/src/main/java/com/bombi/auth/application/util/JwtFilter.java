package com.bombi.auth.application.util;

import static com.bombi.auth.application.jwt.TokenProvider.*;

import com.bombi.auth.application.exception.ResponseWriter;
import com.bombi.auth.application.exception.e401.InvalidTokenException;
import com.bombi.auth.application.jwt.TokenProvider;
import com.bombi.auth.infrastructure.security.CustomUserDetails;
import com.bombi.auth.infrastructure.security.auth.service.CustomUserDetailsService;
import com.bombi.auth.presentation.dto.CommonResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 토큰 필터.
 * Jwt 쿠키로 관리
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
	private final TokenProvider tokenProvider;
	private final CustomUserDetailsService customUserDetailsService;

	private static final List<String> EXCLUDED_PATHS = Arrays.asList(
			"/auth", "/login"
	);

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		log.info("JwtFilter doFilterInternal request url : {}", request.getRequestURI());

		if (EXCLUDED_PATHS.stream().anyMatch(request.getServletPath()::contains)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Cookie[] cookies = request.getCookies();
			if (cookies == null) {
				filterChain.doFilter(request, response);
				return;
			}

			String accessToken = null;
			String refreshToken = null;

			for (Cookie cookie : cookies) {
				if ("access_token".equals(cookie.getName())) {
					accessToken = cookie.getValue();
				} else if ("refresh_token".equals(cookie.getName())) {
					refreshToken = cookie.getValue();
				}
			}

			// 토큰이 둘 다 없으면 필터 패스
			if ((accessToken == null || accessToken.trim().isEmpty()) &&
					(refreshToken == null || refreshToken.trim().isEmpty())) {
				filterChain.doFilter(request, response);
				return;
			}

			String memberId = null;
			CustomUserDetails userDetails = null;

			accessToken = null;

			// Step 1: access_token 존재할 경우만 검증 시도
			if (accessToken != null) {
				try {
					Claims claims = tokenProvider.extractAllClaims(accessToken);
					memberId = claims.getSubject();
					userDetails = customUserDetailsService.loadUserByUsername(memberId);

					if (tokenProvider.validateAccessToken(accessToken, userDetails)) {
						authenticateUser(request, userDetails);
						filterChain.doFilter(request, response);
						return;
					}
				} catch (ExpiredJwtException e) {
					log.warn("Access token expired, fallback to refresh token.");
					memberId = e.getClaims().getSubject();
				} catch (Exception e) {
					log.error("Access token invalid, fallback to refresh token.", e);
				}
			}

			// Step 2: refresh_token으로 재발급 시도
			if (refreshToken != null && !refreshToken.trim().isEmpty()) {
				try {
					tokenProvider.validateRefreshToken(refreshToken);


					if (memberId == null) {
						Claims refreshClaims = tokenProvider.extractAllClaims(refreshToken);
						memberId = refreshClaims.getSubject();
					}

					userDetails = customUserDetailsService.loadUserByUsername(memberId);
					String newAccessToken = tokenProvider.generateAccessToken(userDetails);


					ResponseCookie newAccessTokenCookie = ResponseCookie.from("access_token", newAccessToken)
							.secure(true)
							.httpOnly(true)
							.path("/")
							.sameSite("None")
							.domain("bomnet.shop")
							.maxAge(ACCESS_EXP / 1000)
							.build();
					response.addHeader(HttpHeaders.SET_COOKIE, newAccessTokenCookie.toString());

					authenticateUser(request, userDetails);
					log.debug("Token refreshed and authentication successful for member: {}", memberId);

					filterChain.doFilter(request, response);
					return;
				} catch (Exception e) {
					log.error("Refresh token invalid.", e);
					throw new InvalidTokenException("Refresh token validation failed.");
				}
			}

			// Step 3: 둘 다 실패
			throw new InvalidTokenException("No valid tokens found.");

		} catch (InvalidTokenException e) {
			log.error("Token validation error: {}", e.getMessage());
			ResponseWriter.writeExceptionResponse(
					response,
					HttpServletResponse.SC_UNAUTHORIZED,
					new CommonResponseDto<>("401", "토큰 검증에 실패했습니다.")
			);
		} catch (Exception e) {
			log.error("Authentication processing error", e);
			ResponseWriter.writeExceptionResponse(
					response,
					HttpServletResponse.SC_UNAUTHORIZED,
					new CommonResponseDto<>("401", "인증 처리 중 오류가 발생했습니다.")
			);
		}
	}

	/**
	 * 사용자를 인증 컨텍스트에 설정
	 */
	private void authenticateUser(HttpServletRequest request, CustomUserDetails userDetails) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities()
		);
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}

