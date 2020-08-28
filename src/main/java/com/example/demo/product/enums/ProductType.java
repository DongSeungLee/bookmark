package com.example.demo.product.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ProductType {
    Women(1),
    Men(2),
    Accessories(3),
    Other(4),
    Kids(5),
    Handbags(6);
    private int value;

    public int getValue() {
        return this.value;
    }

    ProductType(int value) {
        this.value = value;
    }

    private static Map<Integer, ProductType> productTypeMap = new HashMap<>();

    static {
        for (ProductType a : ProductType.values()) {
            productTypeMap.put(a.value, a);
        }
    }

    public static ProductType getProductType(int value) {
        return Optional.ofNullable(productTypeMap.get(value))
                .orElseThrow(() -> new IllegalArgumentException("hoho"));
    }
}
