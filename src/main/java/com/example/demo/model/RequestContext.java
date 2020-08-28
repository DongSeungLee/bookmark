package com.example.demo.model;

public class RequestContext {

    private static final ThreadLocal<String> requestHeaderInfoThreadLocal = new ThreadLocal<>();

    public static void create(String applicationType, String requestId) {
        requestHeaderInfoThreadLocal.set("hoho");
    }



    public static void remove() {
        requestHeaderInfoThreadLocal.remove();
    }
}
