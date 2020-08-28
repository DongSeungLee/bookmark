package com.example.demo.book.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(mappedBy = "person",fetch=FetchType.LAZY)
    @JsonManagedReference
    List<Widget> widget = new ArrayList<>();

    public Person(){

    }
    @Builder(toBuilder = true)
    public Person(String name){
        this.name = name;
    }
    public static Person create(String name){
        return builder()
                .name(name)
                .build();
    }
}
