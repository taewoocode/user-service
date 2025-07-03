package com.example.user_service.aop;

import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class RequestLoggingAspect {

	@Pointcut("within(com.example.user_service.user.controller..*)")
	public void onRequest() {
	}

	@Before("onRequest()")
	public void logMethodCall(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String methodName = joinPoint.getSignature().toShortString();
		log.info("=================================================================================================");
		log.info(" ==> Start: " + methodName);
	}

	@After("onRequest()")
	public void logMethodExit(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		String methodName = joinPoint.getSignature().toShortString();
		log.info(" ==> End: " + methodName);
		log.info("=================================================================================================");
	}

	@Around("onRequest()")
	public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		Map<String, String[]> paramMap = request.getParameterMap();

		String params = "";
		if (!paramMap.isEmpty()) {
			params = "[" + paramMapToString(paramMap) + "]";
		}

		long start = System.currentTimeMillis();
		try {
			return pjp.proceed(pjp.getArgs());
		} finally {
			long end = System.currentTimeMillis();
			log.info("Request: {} {} {} < {} ({}ms)",
				request.getMethod(),
				request.getRequestURI(),
				params,
				request.getRemoteHost(),
				end - start
			);
		}
	}

	private String paramMapToString(Map<String, String[]> paramMap) {
		return paramMap.entrySet().stream()
			.map(entry -> {
				String key = entry.getKey();
				String value = String.join(",", entry.getValue());
				return String.format("%s -> (%s)", key, value);
			})
			.collect(Collectors.joining(", "));
	}
} 