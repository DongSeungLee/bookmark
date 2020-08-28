package com.example.demo.junit;

import java.util.HashMap;
import java.util.Map;

public enum Bool {
    False(0),
    True(1);
    public static final int FALSE = 0;
    public static final int TRUE = 1;
    private int value;
    private Bool(int value) { this.value = value; }
    public int getValue() { return value; }

    public static Map<String, Integer> boolMap = new HashMap<>();

    static {
        for (Bool bool : Bool.values()) {
            boolMap.put(bool.toString(), bool.value);
        }
    }
}
