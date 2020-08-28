package com.example.demo.swift;

public class SwiftSocketTimeoutException extends SwiftApiCallException {

    public SwiftSocketTimeoutException(String message, int statusCode) {
        super(message, statusCode);
    }

    public SwiftSocketTimeoutException(String message, int statusCode, String containerName) {
        super(message, statusCode, containerName);
    }
}
