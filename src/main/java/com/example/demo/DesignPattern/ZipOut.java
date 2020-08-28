package com.example.demo.DesignPattern;

public class ZipOut extends Decorator {
    public ZipOut(FileOut fileOut) {
        super(fileOut);
    }
    public void write(byte[] data){
        byte[] encryptedDate = zipOut(data);
        super.doDelegate(encryptedDate);
    }
    private byte[] zipOut(byte[] data){
        StringBuilder builder = new StringBuilder();
        for(byte a :data){
            builder.append(String.format("%2c",a));
        }
        builder.append("zipout!");
        return builder.toString().getBytes();
    }
}
