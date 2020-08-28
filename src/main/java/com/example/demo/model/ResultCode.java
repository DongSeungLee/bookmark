package com.example.demo.model;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(0, true, "success"),
    BAD_REQUEST(400, false, "Invalid syntax for this request was provided."),
    UNAUTHORIZED(401, false, "You are unauthorized for this request."),
    NOT_FOUND(404, false, "We could not find the resource you requested."),
    CONFLICT(409, false, "The request could not be completed due to a conflict with the current state of the resource."),
    INTERNAL_SERVER_ERROR(500, false, "Unexpected internal server error.");

    private int code;
    private boolean isSuccess;
    private String message;

    ResultCode(int code, boolean isSuccess, String message) {
        this.code = code;
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
