package com.example.demo.junit;

import java.util.HashMap;
import java.util.Map;

public enum Weight {
    MustMatch(Integer.MAX_VALUE),
    VeryImportant(5000),
    Important(1000),
    WouldPrefer(100),
    DontCare(0);

    private int value;

    Weight(int value) { this.value = value; }
    public int getValue() { return value; }
    public static Map<String,Integer> weightMap = new HashMap<>();
    static{
        for(Weight weight : Weight.values()){
            weightMap.put(weight.toString(),weight.value);
        }
    }
}
