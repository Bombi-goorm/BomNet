package com.bombi.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LogAspect {

	@Pointcut("execution(* com.bombi.core.application.service..*(..))")
	public void servicePointcut() {}

	@Around("servicePointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Long startTime = System.currentTimeMillis();
		String shortString = joinPoint.getSignature().toShortString();

		log.info("[AOP] {} Start", shortString);

		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Exception e) {
			log.error("[AOP] {} Exception : {}", shortString, e.getMessage());
			throw e;
		} finally {
			long endTime = System.currentTimeMillis();
			long durationMs = endTime - startTime;
			log.info("[AOP] {} End. Duration = {}ms", shortString, durationMs);
		}

		return result;
	}

}
