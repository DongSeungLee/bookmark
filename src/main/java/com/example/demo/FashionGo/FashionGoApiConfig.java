package com.example.demo.FashionGo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FashionGoApiConfig {

    public static String fashionGoApi;

    @Value("${fashionGoApiUrl}")
    public void setFashionGoApi(String endpoint) {
        fashionGoApi = endpoint;
    }
}
