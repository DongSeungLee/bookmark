package com.example.demo.junit;

public class InvariantException extends RuntimeException{
    public InvariantException(){
        super();
    }
    public InvariantException(String str){
        super(str);
    }
}
