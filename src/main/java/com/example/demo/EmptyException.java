package com.example.demo;

public class EmptyException extends RuntimeException{
    public EmptyException(){
        super();
    }
    public EmptyException(String msg){
        super(msg);
    }
}
