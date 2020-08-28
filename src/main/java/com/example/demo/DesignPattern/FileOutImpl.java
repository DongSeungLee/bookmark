package com.example.demo.DesignPattern;

public class FileOutImpl implements FileOut{

    @Override
    public void write(byte[] data) {
        for(byte a :data){
            System.out.print(String.format("%2c",a));
        }
        System.out.println();
    }
}
