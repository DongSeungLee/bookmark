package com.example.demo.model;


import lombok.Builder;

public class JsonResponse {


    private boolean success;
    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    private Object data;
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }


    public JsonResponse(){

    }
    @Builder
    public JsonResponse(boolean success, String reason, String message){
        this.success = success;
        this.reason = reason;
        this.message = message;

    }
}
