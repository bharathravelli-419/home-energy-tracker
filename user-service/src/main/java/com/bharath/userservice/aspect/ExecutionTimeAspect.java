package com.bharath.userservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Pointcut("execution (* com.bharath.userservice.controller.*.*(..)) ")
    public void controllerMethods(){}

    @Around(value = "controllerMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint proceedingJoinPoint){
        long start = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        finally {
            long end = System.currentTimeMillis();
            long elapsedTime = end-start;
            log.info("Controller method:{} execute in {} ms", proceedingJoinPoint.getSignature().getName(),elapsedTime );
        }

    }
}
