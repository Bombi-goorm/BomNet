package com.bombi.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExternalApiLogAspect {

	@Pointcut("execution(* com.bombi.core.infrastructure.external.soil.client..*(..))")
	public void openApiPointcut() {}

	@Around("openApiPointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		String shortString = joinPoint.getSignature().toShortString();

		log.info("[OPEN API] {} Start", shortString);

		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Exception e) {
			log.error("[OPEN API] {} Exception : {}", shortString, e.getMessage());
			throw e;
		} finally {
			long endTime = System.currentTimeMillis();
			long durationMs = endTime - startTime;
			log.info("[OPEN API] {} End. Duration = {}ms", shortString, durationMs);
		}

		return result;
	}
}
