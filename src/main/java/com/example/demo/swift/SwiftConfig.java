package com.example.demo.swift;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
public class SwiftConfig {

    @Bean
    @ConfigurationProperties("cloud.toast.object-storage")
    public SwiftProperties properties(){return new SwiftProperties();}
}
