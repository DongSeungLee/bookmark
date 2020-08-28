package com.example.demo.hoho;

import com.example.demo.hoho.Hoho;

public class Hoho2 implements Hoho {
    private Integer value;

    @Override
    public Integer getValue() {
        return operation(value);
    }
    private Integer operation(Integer value){
        return value+2;
    }
}
