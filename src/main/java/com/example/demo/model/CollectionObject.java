package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollectionObject<T> {
    private List<T> contents;
    private Integer totalCount;
}
