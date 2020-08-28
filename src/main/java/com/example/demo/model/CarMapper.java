package com.example.demo.model;

import com.example.demo.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    @Mapping(source = "numberOfSeats",target = "seatCount")
    @Mapping(source = "type", target="carDtoType")
    CarDto carToCarDto(Car car);
}
