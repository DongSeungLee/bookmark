package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {
    @Scheduled(fixedDelay = 100*1000)
    public void generalScheduler100Second(){
        // scheduler는 기본적으로 thread 하나에서 생성되어서 돌아간다!
        log.info("test general scheduler and current Thread : {}",Thread.currentThread().getName());
    }

    @Scheduled(fixedDelay = 200*1000)
    public void generalScheduler200Second(){
        // scheduler는 기본적으로 thread 하나에서 생성되어서 돌아간다!
        log.info("test 2Min scheduler and current Thread : {}",Thread.currentThread().getName());
    }
}
