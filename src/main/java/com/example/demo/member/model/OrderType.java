package com.example.demo.member.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OrderType {
    Shippping(1),
    Online(2),
    Offline(3);
    private static final Map<Integer, OrderType> valueMap;


    static {
        valueMap = Arrays.stream(OrderType.values())
                .collect(Collectors.toMap(OrderType::getValue, Function.identity()));
    }

    private final int value;

    OrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static OrderType get(int value) {
        return Optional.ofNullable(valueMap.get(value))
                .orElseThrow(() -> new IllegalArgumentException("cannot find a value " + value));
    }
}
