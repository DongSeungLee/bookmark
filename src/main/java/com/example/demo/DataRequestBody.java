package com.example.demo;

import com.example.demo.model.ImageUrls;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DataRequestBody {
    String name;
    Integer age;
    List<String> selectedIndustry = new ArrayList<>();
    ImageUrls imageUrls;
}
