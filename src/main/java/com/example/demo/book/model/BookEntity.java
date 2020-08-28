package com.example.demo.book.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Getter
@Setter
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    @ColumnDefault("true")
    @JsonProperty("active")
    private Boolean active;

    public BookEntity() {

    }

    @Builder
    public BookEntity(String name, Boolean active) {
        this.name = name;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        this.active = this.active == null ? true : this.active;
    }
}

