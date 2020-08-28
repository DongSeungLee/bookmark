package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {
    @Value("${server.port}")
    private Integer port;

    @Bean(name = "serviceHtmlClient")
    public HttpClient serviceHtmlClient() {
        HttpClient httpClient = new HttpClient("http://localhost:" + port, "text/html");
        return httpClient;
    }
}
