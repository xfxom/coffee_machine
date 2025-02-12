package com.coffee.machine.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("within(com.coffee.machine.controller..*) || within(com.coffee.machine.service..*)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Entering method: {}", methodName);
        try {
            Object result = joinPoint.proceed();
            log.info("Exiting method: {} with result: {}", methodName, result);
            return result;
        } catch (Throwable t) {
            log.error("Exception in method: {}: {}", methodName, t.getMessage(), t);
            throw t;
        }
    }
}
