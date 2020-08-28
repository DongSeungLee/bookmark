package com.example.demo.junit;

public class BooleanAnswer {
    private int questionId;
    private boolean value;

    public BooleanAnswer(){

    }
    public BooleanAnswer(int questionId, boolean value) {
        this.questionId = questionId;
        this.value = value;
    }

    boolean getValue() {
        return value;
    }

    int getQuestionId() {
        return questionId;
    }
}
