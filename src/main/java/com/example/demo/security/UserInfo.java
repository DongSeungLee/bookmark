package com.example.demo.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String username;
    private String password;
    public UserInfo(){

    }
    public UserInfo(String username,String password){
        this.username = username;
        this.password = password;
    }
}
