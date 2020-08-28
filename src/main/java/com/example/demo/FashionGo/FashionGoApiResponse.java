package com.example.demo.FashionGo;

import com.example.demo.model.ApiResponseHeader;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FashionGoApiResponse<T> {
    private ApiResponseHeader header;
    private T data;
}
