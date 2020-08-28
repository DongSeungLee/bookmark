package com.example.demo.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto {
    private Integer orderId;
    private Integer memberId;
    private String orderName;
    private String memberName;
    private OrderType orderType;
    private LocalDateTime createdOn;

    public OrderDto() {

    }

    @Builder
    public OrderDto(Integer orderId, Integer memberId, String orderName, String memberName,
                    OrderType orderType, LocalDateTime createdOn) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.orderName = orderName;
        this.memberName = memberName;
        this.orderType = orderType;
        this.createdOn = createdOn;

    }
}
