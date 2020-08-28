package com.example.demo.annotation;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class MyContextContainer {
    public MyContextContainer(){}
    private <T> T invokeAnnonations(T instance) throws IllegalAccessException {
        Field [] fields = instance.getClass().getDeclaredFields();
        for( Field field : fields ){
            MyAnnotation annotation = field.getAnnotation(MyAnnotation.class);
            if( annotation != null && field.getType() == String.class ){
                field.setAccessible(true);
                if(field.getName().equals("parent")){
                    field.set(instance, annotation.name());
                }
                else{
                    field.set(instance, annotation.value());
                }
            }
        }
        return instance;
    }
    public <T> T get(Class clazz) throws IllegalAccessException, InstantiationException {
        T instance = (T) clazz.newInstance();
        instance = invokeAnnonations(instance);
        return instance;
    }
}
