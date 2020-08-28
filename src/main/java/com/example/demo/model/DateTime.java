package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Getter
@Setter
public class DateTime {
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime datetime = LocalDateTime.now();
    private String name = "LeeDongSeung";
    private int age = 29;

}
