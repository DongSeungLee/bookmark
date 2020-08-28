package com.example.demo.book.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Getter
@Entity
@Setter
@Table(name = "widget")
public class Widget {
    @Column(name = "name")
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="person_id")
    @JsonBackReference
    private Person person;
    public void setPerson(Person person){
        this.person = person;
    }
    Widget() {

    }

    public Widget(Integer id,String name){
        this.id = id;
        this.name = name;
    }
    public static Widget create(String name, Integer id) {
        return builder()
                .name(name)

                .build();
    }

    @Builder(toBuilder = true)
    Widget(String name, Integer id) {
        this.name = name;

    }
    private static StringBuilder b = new StringBuilder();

    public void manipulateName(){
        b.setLength(0);
        b.append(this.name);
        b.append("hoho");
        this.name = b.toString();
        return;
    }
}
