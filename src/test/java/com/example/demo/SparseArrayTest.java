package com.example.demo;

import com.example.demo.junit.ContainsAllElement;
import com.example.demo.junit.SparseArray;
import org.junit.Test;
import org.junit.Before;
import org.junit.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.demo.junit.ContainsAllElement.ContainsAllElement;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
public class SparseArrayTest {
    private SparseArray<String> array;

    @Before
    public void SparseArrayTest() {
        array = new SparseArray<>();
    }
    @Test
    public void handlesInsertionInDescendingOrder() throws NoSuchAlgorithmException {
        array.put(7, "seven");
        array.checkInvariants();
        array.put(6, "six");
        array.checkInvariants();
        array.put(5, "five");
        assertThat(array.get(6), equalTo("six"));
        assertThat(array.get(7), equalTo("seven"));
        assertThat(array.get(5), equalTo("five"));
        assertThat(array, ContainsAllElement(array));
        System.out.println(byte2Hex(sha256("zenana")));
    }
    @Test
    public void SHA256Test() throws NoSuchAlgorithmException {
        String str = "zenana";
        byte[] strAfterSha = sha256(str);
        System.out.println(byte2Hex(strAfterSha));
        System.out.println(byte2Hex(sha256("zenana")));

    }
    private byte[] sha256(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        return md.digest();

    }
    private String byte2Hex(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for(byte b : bytes){
            builder.append(String.format("%02x",b));
        }
        return builder.toString();
    }
}
