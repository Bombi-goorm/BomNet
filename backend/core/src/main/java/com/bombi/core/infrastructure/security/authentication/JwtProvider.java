package com.bombi.core.infrastructure.security.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import com.bombi.core.common.exception.TokenNotFoundException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * 토큰 유효성 검사
     */
    public boolean validateToken(String token) {
        if(token == null) {
            log.info("JwtProvider::validateToken - access token is null");
            throw new TokenNotFoundException("token is null");
        }

        try {
            // 토큰 파싱
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Check Member ID
            String memberId = claims.getSubject();
            if (memberId == null) {
                log.warn("JwtProvider::validateToken member ID is null");
                return false;
            }

            return true;
        } catch (JwtException | IllegalStateException exception) {
            log.warn("JwtProvider::validateToken 유효하지 않은 토큰: {}", exception.getMessage());
            return false;
        }
    }


    /**
     * 토큰에서 사용자 이름(username = email) 추출
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 토큰에서 권한 정보 추출
     */
    public List<String> getRolesFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("ROLES", List.class);
    }

    public Claims extractAllClaims(String accessToken) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();
    }
}
