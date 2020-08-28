package com.example.demo.DesignPattern;

public class EncryptionOut extends Decorator{
    public EncryptionOut(FileOut fileOut) {
        super(fileOut);
    }
    public void write(byte[] data){
        byte[] encryptedDate = encrypt(data);
        super.doDelegate(encryptedDate);
    }
    private byte[] encrypt(byte[] data){
        StringBuilder builder = new StringBuilder();
        for(byte a :data){
            builder.append(String.format("%2c",a));
        }
        builder.append("encrypt");
        return builder.toString().getBytes();
    }
}
