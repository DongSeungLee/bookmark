package com.example.demo.member.model;

import com.example.demo.LocalDateTimeConverter;
import com.example.demo.member.OrderTypeConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "tblOrder")
@Getter
@Setter
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "name",nullable = true)
    private String name;

    @Column(name = "createdOn", updatable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdOn;

    @Column(name = "order_type")
    @Convert(converter = OrderTypeConverter.class)
    private OrderType orderType;

    @Column(name = "member_id")
    private Integer memberId;

    @Builder
    public OrderEntity(Integer orderId, String name, LocalDateTime createdOn, OrderType orderType, Integer memberId) {
        this.orderId = orderId;
        this.name = name;
        this.createdOn = createdOn;
        this.orderType = orderType;
        this.memberId = memberId;
    }

    public static OrderEntity create(String name, OrderType orderType, Integer memberId) {
        return builder()
                .name(name)
                .createdOn(LocalDateTime.now())
                .orderType(orderType)
                .memberId(memberId)
                .build();
    }
}
