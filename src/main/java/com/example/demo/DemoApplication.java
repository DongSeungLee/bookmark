package com.example.demo;

import com.example.demo.AOP.Inter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeoutException;


@EnableCaching
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@EnableScheduling
public class DemoApplication {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class, RedisCacheConfiguration.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);

        // what is classLoader, and Class<?>( what is this?) Class<?>[]{Integer.class}는 말 그대로 Integer.Class를 원소를 갖는 하나의 Class array라는 것인가?
        Inter inter = (Inter) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{Inter.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                System.out.println("in Proxy!!!");
                return method.invoke((Inter) () -> "hoho\n"
                        , args);
            }
        });
        // Inter is Functional Interface which contains toMessage method only!
        // then can i squash all the commits before?
        System.out.print(inter.toMessage());
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("512MB"));
        factory.setMaxRequestSize(DataSize.parse("512MB"));
        return factory.createMultipartConfig();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(512000000);

        return multipartResolver;
    }

}
