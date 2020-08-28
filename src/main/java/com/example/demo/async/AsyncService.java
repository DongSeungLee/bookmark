package com.example.demo.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    @Async("dsThreadPoolTaskExecutor")
    public void run(Runnable runnable){
        runnable.run();
    }
}
