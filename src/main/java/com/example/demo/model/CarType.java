package com.example.demo.model;

public enum CarType {
    Sedan("sedan"),
    SUV("suv");
    String message;
    CarType(String message){
        this.message = message;
    }
}
