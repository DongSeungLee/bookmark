package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class TestJson {
    private String originalImageUrl;
    private String thumbnailUrl;
    private Long bookmarkId;
    private String createdOn;
}
