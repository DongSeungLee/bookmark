package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum AddressType {
    SHOWROOM(1),
    BILLING(2),
    WAREHOUSE(3),
    SHIPPING(4),
    COMPANY(5);

    private int value;

    AddressType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    private static Map<Integer, AddressType> addressTypeMap = new HashMap<>();
    static {
        for (AddressType a : AddressType.values()) {
            addressTypeMap.put(a.getValue(), a);
        }
    }

    public static AddressType get(int value) {
        return Optional.ofNullable(addressTypeMap.get(value))
                .orElseThrow(() -> new IllegalArgumentException("cannot find a value, " + value));
    }
}
