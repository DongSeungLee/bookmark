package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseHeader {

    @JsonProperty("isSuccessful")
    private boolean isSuccessful;
    private int resultCode;
    private String resultMessage;

    public static ApiResponseHeader create(boolean isSuccessful, int resultCode, String resultMessage) {

        return builder()
                .isSuccessful(isSuccessful)
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build();
    }

    public static ApiResponseHeader create(ResultCode resultCode) {

        return builder()
                .isSuccessful(resultCode.isSuccess())
                .resultCode(resultCode.getCode())
                .resultMessage(resultCode.getMessage())
                .build();
    }
}
