package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component("SetterMonitor")
public class SetterMonitor {

    // com.example.demo.Home class에 있는 것들에 모두 적용하겠다.
    @Around("execution(* com.example.demo.Home.*(..))")
    public Object callMethodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = joinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;
        Signature sig = joinPoint.getSignature();
        //logger.info(sig.getDeclaringTypeName() + "#" + sig.getName() + "(" + args.replaceAll(" ", "") + ")\t" + elapsed);
        log.info(sig.getDeclaringTypeName() + "#" + sig.getName() + "\t" + elapsed);
        return retVal;
    }
}
