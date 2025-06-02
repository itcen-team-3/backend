package com.team_3.nursing_care.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

   // @Pointcut("execution(* com.team_3.nursing_care.domain.care_log.controller.*.*(..))")
    private void care_log_proxy_cut() {
    }
   // @Pointcut("execution(* com.team_3.nursing_care.domain.geofence.controller.*.*(..))")
    private void geofence_proxy_cut() {
    }

   // @Around("care_log_proxy_cut() || geofence_proxy_cut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Method method = getMethod(joinPoint);
        log.info("Method Log: {} || Args: {}", method.getName(), Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.info("Method {} is finished || Execution Time: {} ms", method.getName(), executionTime);
        return result;
    }


//    @AfterThrowing(pointcut = "care_log_proxy_cut() || geofence_proxy_cut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        Method method = getMethod(joinPoint);
        log.error("AfterThrowing Method: {} || Exception: {}", method.getName(), exception.getMessage());
        log.error("Exception type: {}", exception.getClass().toGenericString());
        log.error("Exception point: {}", exception.getStackTrace()[0]);
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

}
