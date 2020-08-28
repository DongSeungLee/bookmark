package com.example.demo.member;

import com.example.demo.member.model.OrderType;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class OrderTypeConverter implements AttributeConverter<OrderType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderType attribute) {
        if (Objects.isNull(attribute)) {
            new IllegalArgumentException("OrderType must not be null");
        }
        return attribute.getValue();
    }

    @Override
    public OrderType convertToEntityAttribute(Integer dbData) {
        return OrderType.get(dbData);
    }
}
