package com.example.demo.hoho;

import com.example.demo.model.DongSeung;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HohoConfig {
    @Bean(name="dongseung")
    @ConfigurationProperties("my")
    public DongSeung localDongSeong()
    {
        DongSeung aa = new DongSeung();
        System.out.println("here "+aa.getDriverClassName());
        return new DongSeung();
    }
    @Bean(name = "hoho2")
    public Hoho localHoho2(){


        return new Hoho2();
    }
    @Bean(name = "hoho123")
    public Hoho localHoho1(){
        return new Hoho1();
    }
}
