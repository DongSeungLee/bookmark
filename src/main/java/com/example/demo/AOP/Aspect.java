package com.example.demo.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    //@Around("excution(com.example.demo.*)")
    @Around("@annotation(com.example.demo.PerLogging)")
    public Object method(ProceedingJoinPoint pjp)throws Throwable{
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈
        System.out.println("PerLoggin :" + (System.currentTimeMillis() - begin));
        return retVal;
    }
    @Around("@annotation(com.example.demo.AOP.DoRepeat)")
    public void doRepeat(ProceedingJoinPoint pjp)throws Throwable{
        MethodSignature sig = (MethodSignature)pjp.getSignature();
        if(sig.getReturnType().equals(void.class)){
            return;
        }
        log.info("do repeat start");
        log.info("current Spring AOP : {}",pjp.getThis());
        log.info("current Spring AOP target : {}",pjp.getTarget());

        for(int i=0;i<10;i++){
            pjp.proceed();
        }
        log.info("do repeat end!");
    }
}
