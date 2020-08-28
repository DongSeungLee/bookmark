package com.example.demo.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;

    public ProductEntity() {

    }

    @Builder
    public ProductEntity(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
