package com.example.demo.Utiliy;

import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class Crypto {
    public static byte[] sha256(byte[] value)throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(value);
    }
    public static Optional<String> encodeBase64UrlSignValue(String source){
        if(StringUtils.isEmpty(source)){
            return Optional.empty();
        }
        try{
            return Optional.ofNullable(Base64Utils.encodeToUrlSafeString(sha256(source.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            return Optional.empty();
        }
    }
}
