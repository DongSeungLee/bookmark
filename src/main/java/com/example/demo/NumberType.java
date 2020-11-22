package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum NumberType {
    SMALL(0),
    LARGE(50_000);
    int value;

    NumberType(int value) {
        this.value = value;
    }

    int getValue() {
        return this.value;
    }

    private static Map<Integer, NumberType> mapNumberType = new HashMap<>();

    static {
        for (NumberType values : NumberType.values()) {
            mapNumberType.put(values.getValue(), values);
        }
    }

    public NumberType getNumberType(int value) {
        return Optional.ofNullable(mapNumberType.get(value))
                .orElseThrow(() -> new IllegalArgumentException("hoho"));
    }
}
