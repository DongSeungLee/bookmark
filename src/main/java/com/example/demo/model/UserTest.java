package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTest {
    public String id;
    public String pwd;
    public String name;
    public Integer birthDate;

    public UserTest(){}

    public UserTest(String id, String pwd, String name, Integer birthDate) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "id : " + id + ", pwd : " + pwd + ", name : " + name +", birthdate : " + birthDate;
    }

    // setter , getter 생략
}
