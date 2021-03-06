package com.example.demo.swift;

public class SwiftConnectionTimeoutException extends SwiftApiCallException {

    public SwiftConnectionTimeoutException(String message, int statusCode) {
        super(message, statusCode);
    }

    public SwiftConnectionTimeoutException(String message, int statusCode, String containerName) {
        super(message, statusCode, containerName);
    }
}
