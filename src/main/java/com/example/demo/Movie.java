package com.example.demo;

public class Movie {
    private int sumOfRate = 0;
    private int countOfRate = 0;

    public Integer averageRating() {
        if (countOfRate == 0) return 0;
        return sumOfRate / countOfRate;
    }

    public void rate(int rate) {
        sumOfRate += rate;
        countOfRate++;
    }
}
