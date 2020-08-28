package com.example.demo;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component("CustomErrorAttributes")
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> result = super.getErrorAttributes(webRequest, includeStackTrace);
        result.put("greeting", "Hello");
        result.put("hoho","HOHO");
        return result;
    }
}
