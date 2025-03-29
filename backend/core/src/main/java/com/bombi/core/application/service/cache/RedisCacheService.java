package com.bombi.core.application.service.cache;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

	private final RedisTemplate<String, List<VarietyPriceInfo>> redisTemplate;
	// private final RedisTemplate<String, Object> redisTemplate;

	public List<VarietyPriceInfo> getFromCache(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void extendTTL(String key) {
		Long currentTTL = redisTemplate.getExpire(key, TimeUnit.HOURS);

		if (currentTTL == null || currentTTL < 0) {
			currentTTL = 0L;
		}

		long extendedTTL = currentTTL + TimeUnit.HOURS.toHours(1);
		redisTemplate.expire(key, extendedTTL, TimeUnit.HOURS);
	}

	public void setCacheWithTime(String key, List<VarietyPriceInfo> fallbackVarietyPriceInfos, Duration duration) {
		redisTemplate.opsForValue().set(key, fallbackVarietyPriceInfos, duration);
	}

}
