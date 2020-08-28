package com.example.demo.junit;

import com.example.demo.EmptyException;
import com.example.demo.junit.Scoreable;

import java.util.ArrayList;
import java.util.List;

public class ScoreCollection {
    private List<Scoreable> scores = new ArrayList<>();
    public void add(Scoreable scoreable){
        scores.add(scoreable);
    }
    public int arithmeticMean(){
        if(scores.isEmpty()){
            throw new EmptyException("Empty List!");
        }
        int total = scores.stream().mapToInt(Scoreable::getScore).sum();
        return total / scores.size();
    }
}
