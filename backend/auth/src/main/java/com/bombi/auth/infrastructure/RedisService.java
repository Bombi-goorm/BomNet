package com.bombi.auth.infrastructure;

import com.bombi.auth.application.exception.e500.RedisSessionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    /**
     * Redis 키 생성
     */
    private String generateRedisKey(String prefix, String id) {
        return prefix + ":" + id;
    }

    /**
     * 상태 삭제 (인증 성공, 로그아웃, 탈퇴)
     */
    public void resetStatus(String id) {
        String refreshKey = generateRedisKey(Constants.REFRESH_TOKEN_PREFIX, id);
        try {
            redisTemplate.delete(refreshKey);
            log.debug("실패 카운트 초기화: id={}", id);
        } catch (Exception e) {
            log.error("Redis 상태 초기화 실패: ", e);
            throw new RedisSessionException("사용자 정보 처리 중 오류 발생");
        }
    }

    /**
     * Refresh 토큰 검증 문자열 저장
     */
    public void setRefreshTokenVerification(String id, String verifyString, long expiration) {
        String key = generateRedisKey(Constants.REFRESH_TOKEN_PREFIX, id);
        try {
            redisTemplate.opsForValue().set(key, verifyString, expiration, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Redis 저장 실패 - key={}, exp={}ms", key, expiration, e);
            e.printStackTrace();
            throw new RedisSessionException("토큰 정보 저장 중 오류 발생");
        }
    }

    /**
     * Refresh 토큰 검증 문자열 조회
     */
    public String getRefreshTokenVerification(String id) {
        String key = generateRedisKey(Constants.REFRESH_TOKEN_PREFIX, id);
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Redis 조회 실패 - key={}", key, e);
            throw new RedisSessionException("토큰 정보 조회 중 오류 발생");
        }
    }
}
