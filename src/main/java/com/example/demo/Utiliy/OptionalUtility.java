package com.example.demo.Utiliy;

import java.util.Optional;

public class OptionalUtility {
    public static Optional<Integer> stringToInt(String str) {
        try {
            return Optional.ofNullable(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
