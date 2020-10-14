package com.example.demo.async;

import com.example.demo.PerLogging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    private final AsyncService asyncService;

    public TestService(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    public void func(int i) {
        asyncHello(i);
    }


    private void asyncHello(int i) {
        asyncService.run(() -> {
            log.info("async i :" + i);
        });
    }

    @Async("dsThreadPoolTaskExecutor")
    public void hoho() {
        for (int i = 0; i < 100; i++) {
            log.info("async test service in hoho method : {}", i);
        }
        return;
    }

    @PerLogging
    public void method() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method");
    }
}
