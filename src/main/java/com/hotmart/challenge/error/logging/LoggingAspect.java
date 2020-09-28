/**
 * 
 */
package com.hotmart.challenge.error.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Aspect for logging execution of service and repository Spring components.
 * 
 * @author junio
 *
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

	@Pointcut("within(com.hotmart.challenge.controller..*)")
	public void applicationPackagePointcut() {
		// Method is empty as this is just a Pointcut,
		// the implementations are in the advices.
	}

	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint for advice
	 * @return result
	 * @throws Throwable throws IllegalArgumentException
	 */
	@Around("applicationPackagePointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("Enter: {}.{}() with argument(s) = {}", 
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), 
				Arrays.toString(joinPoint.getArgs()));
		
		try {
			Object result = joinPoint.proceed();
			log.info("Exit: {}.{}() with result = {}", 
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), 
					result);
			
			return result;
			
		} catch (IllegalArgumentException ex) {
			log.error("Illegal argument: {} in {}.{}()", 
					Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), 
					joinPoint.getSignature().getName());
			throw ex;
		}
	}
}
