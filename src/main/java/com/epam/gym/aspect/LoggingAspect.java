package com.epam.gym.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.epam.gym..*(..))")
    public Object handleServiceExceptions(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            Signature signature = joinPoint.getSignature();
            logger.error("{}.{}(): {}", signature.getDeclaringTypeName(), signature.getName(), ex.getMessage());

            return null;
        }
    }
}
