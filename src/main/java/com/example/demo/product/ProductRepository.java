package com.example.demo.product;

import com.example.demo.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
    Optional<ProductEntity> findByPrice(double price);
    List<ProductEntity> findProductEntitiesByPrice(double price);
}
