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


    private void asyncHello(int j) {
        // 이렇게 해야 async로 함수 10번을 호출한다.
        // 이때 설정한 queue의 갯수보다 많은 thread를 실행하려 한다면(빠른 시간내에) RejectedExecutionException가 발생한다.
        for (int i = 0; i < j; i++) {
            int finalI = i;
            asyncService.run(() -> {
                log.info("async i :" + finalI);
            });
        }

    }

    @Async("dsThreadPoolTaskExecutor")
    public void hoho() throws InterruptedException {
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            log.info("async test service in hoho method : {} and {}", i, Thread.currentThread().getName());
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
