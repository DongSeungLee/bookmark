package com.example.demo.model;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private ResultCode resultCode;
    public NotFoundException(String message, ResultCode resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    public NotFoundException(String message, Throwable e, ResultCode resultCode) {
        super(message, e);
        this.resultCode = resultCode;
    }
}
