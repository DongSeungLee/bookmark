package com.example.demo.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAnnotation {
    String value() default "MyAnnotation is applied!";
    String name() default "MyAnnotation name";
}
