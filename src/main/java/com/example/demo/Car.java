package com.example.demo;

import com.example.demo.model.CarType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {
    private String make;
    private int numberOfSeats;
    private CarType type;
    public Car(){

    }
    public Car(String make,int numberOfSeats,CarType type){
        this.make = make;
        this.numberOfSeats = numberOfSeats;
        this.type = type;
    }
}
