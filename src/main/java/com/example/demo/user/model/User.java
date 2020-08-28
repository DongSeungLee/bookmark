package com.example.demo.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "age")
    private Integer age;
    public User(){

    }
    @Builder
    public User(String name,String email,int age){
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
