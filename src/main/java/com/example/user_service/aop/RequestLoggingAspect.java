package com.example.user_service.aop;

import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
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

	@Pointcut("within(com.example.user_service..controller..*)")
	public void controllerLayer() {
	}

	@Pointcut("within(com.example.user_service..service..*)")
	public void serviceLayer() {
	}

	@Pointcut("within(com.example.user_service..repository..*)")
	public void repositoryLayer() {
	}

	// Controller 계층
	@Before("controllerLayer()")
	public void logController(JoinPoint joinPoint) {
		log.info("[CONTROLLER] Start: {}", joinPoint.getSignature());
	}

	@After("controllerLayer()")
	public void logControllerEnd(JoinPoint joinPoint) {
		log.info("[CONTROLLER] End: {}", joinPoint.getSignature());
	}

	@Around("controllerLayer()")
	public Object logControllerAround(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			return pjp.proceed(pjp.getArgs());
		} catch (Throwable ex) {
			log.error("[CONTROLLER] EXCEPTION in {}: {}", pjp.getSignature(), ex.getMessage(), ex);
			throw ex;
		} finally {
			long end = System.currentTimeMillis();
			log.info("[CONTROLLER] Execution time for {}: {}ms", pjp.getSignature(), end - start);
		}
	}

	// Service 계층
	@Before("serviceLayer()")
	public void logService(JoinPoint joinPoint) {
		log.info("[SERVICE] Start: {}", joinPoint.getSignature());
	}

	@After("serviceLayer()")
	public void logServiceEnd(JoinPoint joinPoint) {
		log.info("[SERVICE] End: {}", joinPoint.getSignature());
	}

	@Around("serviceLayer()")
	public Object logServiceAround(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			return pjp.proceed(pjp.getArgs());
		} catch (Throwable ex) {
			log.error("[SERVICE] EXCEPTION in {}: {}", pjp.getSignature(), ex.getMessage(), ex);
			throw ex;
		} finally {
			long end = System.currentTimeMillis();
			log.info("[SERVICE] Execution time for {}: {}ms", pjp.getSignature(), end - start);
		}
	}

	// Repository 계층
	@Before("repositoryLayer()")
	public void logRepository(JoinPoint joinPoint) {
		log.info("[REPOSITORY] Start: {}", joinPoint.getSignature());
	}

	@After("repositoryLayer()")
	public void logRepositoryEnd(JoinPoint joinPoint) {
		log.info("[REPOSITORY] End: {}", joinPoint.getSignature());
	}

	@Around("repositoryLayer()")
	public Object logRepositoryAround(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			return pjp.proceed(pjp.getArgs());
		} catch (Throwable ex) {
			log.error("[REPOSITORY] EXCEPTION in {}: {}", pjp.getSignature(), ex.getMessage(), ex);
			throw ex;
		} finally {
			long end = System.currentTimeMillis();
			log.info("[REPOSITORY] Execution time for {}: {}ms", pjp.getSignature(), end - start);
		}
	}

	@Around("within(com.example.user_service..controller..*)")
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
		} catch (Throwable ex) {
			log.error("EXCEPTION in {}: {}", pjp.getSignature(), ex.getMessage(), ex);
			throw ex;
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