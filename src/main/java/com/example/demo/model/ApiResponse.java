package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse {
    private ApiResponseHeader header;
    private ApiResponseBody data;
    public ApiResponse(){}
    @Builder
    public ApiResponse(ApiResponseHeader header, ApiResponseBody data) {
        this.header = header;
        this.data = data;
    }
}
