package com.example.demo.junit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

public class BooleanQuestion extends Question {
    public BooleanQuestion(int id, String text) {
        super(id, text, new String[] { "No", "Yes" });
    }

    @Override
    public boolean match(int expected, int actual) {
        return expected == actual;
    }
}
