package com.bombi.core.aop;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {

	private final RedisTemplate<String, List<VarietyPriceInfo>> redisTemplate;

	@Pointcut("@annotation(com.bombi.core.common.annotation.CacheableData) && args(item,..)")
	public void cacheableData(String item) {}

	@Around("cacheableData(item)")
	public Object around(ProceedingJoinPoint joinPoint, String item) throws Throwable {
		String cacheKey = createCacheKey(item);
		List<VarietyPriceInfo> cacheValue = redisTemplate.opsForValue().get(cacheKey);

		// 캐시가 있는 경우 반환
		if (cacheValue != null && !cacheValue.isEmpty()) {
			log.info("캐시 Hit");
			return cacheValue;
		}

		// 초기 조회 로직 수행
		log.info("캐시 Miss");
		List<VarietyPriceInfo> result = (List<VarietyPriceInfo>) joinPoint.proceed();

		// 결과를 캐시에 저장
		if (result != null && !result.isEmpty()) {
			log.info("새로운 값 캐시에 저장");
			redisTemplate.opsForValue().set(cacheKey, result, Duration.ofHours(1L));
		}

		return result;
	}

	private String createCacheKey(String item) {
		return "RealTimePrice::" + item;
	}
}
