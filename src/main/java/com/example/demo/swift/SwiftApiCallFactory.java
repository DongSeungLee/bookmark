package com.example.demo.swift;

import org.springframework.stereotype.Component;

/**
 * 요청이 있을 때마다 Singleton으로 만든 Factory에 대해서
 * SwiftApiCaller를 생성한다.
 */
@Component
public class SwiftApiCallFactory {

    private SwiftAuth auth;
    private SwiftPropertyInterface properties;

    public SwiftApiCallFactory(SwiftAuth auth, SwiftPropertyInterface properties) {
        this.auth = auth;
        this.properties = properties;
    }

    public SwiftApiCaller create() {
        return new SwiftApiCaller(auth, properties.getApiUrl() + properties.getAccount());
    }
}
