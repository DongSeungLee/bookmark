package com.example.demo.async;

import com.example.demo.PerLogging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class TestService {
    @Autowired
    AsyncService asyncService;
    public void func(int i){
        asyncHello(i);
    }


    private void asyncHello(int i){
        asyncService.run(()->{
            log.info("async i :"+i);
        });
    }
    @Async("dsThreadPoolTaskExecutor")
    public void hoho(){
        for(int i=0;i<100;i++){
            log.info("asynch test service in hoho method : {}",i);
        }
        return;
    }
    @PerLogging
    public void method(){
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("method");
    }
}
