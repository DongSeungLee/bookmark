package com.example.demo.model;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Before;

@Aspect
@Configuration
@Order(0)
public class UserInfoCheckerAspect {

    @Before("@annotation(com.example.demo.model.UserInfoCheck)")
    public void checkUserInfoExistence(JoinPoint joinPoint) {
        System.out.println("hoho UserInfoCheck!");
    }
}
