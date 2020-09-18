package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class MyRedisTemplate<T> {
    @Autowired
    private RedisTemplate redisTemplate;
    public void setValue(String name, T entity, long time, TimeUnit var5){
        redisTemplate.opsForValue().set(name,entity,time,var5);
    }
}
