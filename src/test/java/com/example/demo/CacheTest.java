package com.example.demo;


import com.example.demo.model.FieldExam;
import com.example.demo.model.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public class CacheTest {

    @Autowired
    private CacheManager cacheManager;


    private CacheService cacheService = new CacheService();

    @Test
    public void test() throws InterruptedException {
        FieldExam fieldExam = new FieldExam();
        fieldExam.setF1(1);
        fieldExam.setF2(2);
        Cache cache = cacheManager.getCache("method");
        cache.put("title","ehcache testing......");
        System.out.println(cache.get("title").get());
        System.out.println("hoho!!!!!!!"+cacheManager.getCache("method"));
        Thread.sleep(21*1000);
        System.out.println("hoho"+cacheManager.getCache("method"));
        System.out.println(cache.get("title"));

    }

    @Test
    public void testhoho() throws NoSuchMethodException {
        Arrays.asList(String.class.getDeclaredFields()).forEach(a->
                System.out.println(a.getName()));
        System.out.println(String.class.getDeclaredFields());
        System.out.println(UserTest.class.getMethod("toString").getParameterTypes());
        Arrays.asList(UserTest.class.getMethod("toString").getParameterTypes()).forEach(a->
                System.out.println(a.getName()));
    }
}
