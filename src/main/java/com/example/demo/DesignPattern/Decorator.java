package com.example.demo.DesignPattern;

public abstract class Decorator implements FileOut{
    private FileOut delegate;
    public Decorator(FileOut fileOut){
        this.delegate = fileOut;
    }

    protected void doDelegate(byte[] data){
        delegate.write(data);
    }
    @Override
    public void write(byte[] data){
        for(byte a : data){
            System.out.print(String.format("%2c",a));
        }
        System.out.println();
    }
}
