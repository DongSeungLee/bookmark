package com.example.demo;

@FunctionalInterface
public interface DSPredicate<T> {
    boolean test(T a);
}
