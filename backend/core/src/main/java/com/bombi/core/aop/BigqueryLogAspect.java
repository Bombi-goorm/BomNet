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
public class BigqueryLogAspect {

	@Pointcut("@annotation(com.bombi.core.common.annotation.BigQueryData)")
	public void bigQueryDataPointcut() {}

	@Around("bigQueryDataPointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		String shortString = joinPoint.getSignature().toShortString();

		log.info("[BigQuery] {} Start", shortString);

		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Exception e) {
			log.error("[BigQuery] {} Exception : {}", shortString, e.getMessage());
			throw e;
		} finally {
			long endTime = System.currentTimeMillis();
			long durationMs = endTime - startTime;
			log.info("[BigQuery] {} End. Duration = {}ms", shortString, durationMs);
		}

		return result;
	}
}
