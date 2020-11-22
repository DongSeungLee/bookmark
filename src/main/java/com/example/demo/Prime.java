package com.example.demo;

import java.util.List;

public class Prime {
    public static boolean isPrime(List<Integer> list, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return TakeWhile.takeWhile(list, i -> i <= candidateRoot)
                .stream().noneMatch(p -> candidate % p == 0);
    }
}
