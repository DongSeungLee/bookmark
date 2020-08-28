package com.example.demo.member.repository;

import com.example.demo.member.model.OrderEntity;
import com.example.demo.member.model.OrderType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void save(){
        OrderEntity o1 = OrderEntity.create("hoho1", OrderType.Shippping,1);
        OrderEntity o2 = OrderEntity.create("hoho2", OrderType.Online,2);
        OrderEntity o3 = OrderEntity.create("hoho3", OrderType.Offline,3);
        orderRepository.saveAll(Arrays.asList(o1,o2,o3));
    }
}
