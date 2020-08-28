package com.example.demo.member.repository;

import com.example.demo.member.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
}
