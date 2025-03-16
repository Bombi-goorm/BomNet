package com.bombi.core.infrastructure.security.authentication;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.common.exception.InvalidTokenException;
import com.bombi.core.common.exception.TokenNotFoundException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    public static final long ACCESS_TOKEN_COOKIE_AGE = 60L;
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
        "/core/health", "/bigquery/data", "/gcs/data", "/weather/special", "/naver/news", "/best/price", "/core/home", "/weather/forecast", "/soil/character", "/soil/chemical",
        "/farm", "/product/chart/node", "/product/chart/link", "/product/chart", "/core/item/price"
    );
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    private final JwtProvider jwtProvider;
    private final JwtGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtAuthenticationFilter::doFilterInternal");

        if (EXCLUDED_PATHS.stream().anyMatch(request.getServletPath()::contains)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 쿠키에서 토큰 추출
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = extractTokenFromCookie(ACCESS_TOKEN_COOKIE_NAME, cookies);
            String refreshToken = extractTokenFromCookie(REFRESH_TOKEN_COOKIE_NAME, cookies);

            String memberId;

            try {
                jwtProvider.validateToken(accessToken);
                Claims claims = jwtProvider.extractAllClaims(accessToken);
                memberId = claims.getSubject();
            } catch (ExpiredJwtException | TokenNotFoundException e) {
                log.info("access token expired or not found. start renewing token");
                String renewedAccessToken = jwtGenerator.renewToken(accessToken, refreshToken);

                // set-cookie의 access_token에 새로 발급한 토큰을 지정
                addNewTokenCookieInHeader(response, renewedAccessToken);

                Claims claims = jwtProvider.extractAllClaims(renewedAccessToken);
                memberId = claims.getSubject();
            }

            if(memberId != null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId);
                authenticateUser(request, userDetails);
            }

        } catch (InvalidTokenException e) {
            log.error("Token validation error: {}", e.getMessage());
            deleteAccessTokenInCookie(response);
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "토큰 검증에 실패했습니다.");
            return;
        } catch (Exception e) {
            log.error("Authentication processing error", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "인증 처리 중 오류가 발생했습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookie(String tokenName, Cookie[] cookies) {
        return Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(tokenName))
            .findFirst()
            .map(Cookie::getValue)
            .orElse(null);
    }

    private void addNewTokenCookieInHeader(HttpServletResponse response, String accessToken) {
        ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, accessToken)
            .secure(true)
            .httpOnly(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(ACCESS_TOKEN_COOKIE_AGE)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void deleteAccessTokenInCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, null)
            .secure(true)
            .httpOnly(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(0)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private static void sendErrorResponse(HttpServletResponse response, int scUnauthorized, String message) {
        ResponseWriter.writeExceptionResponse(
            response,
            scUnauthorized,
            new CoreResponseDto<>("401", message)
        );
    }
}
