package com.example.demo.model;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class BookmarkBody implements ApiResponseBody {
    List<ImageUrls> contents = new ArrayList<>();
    List<String> searchURLs = new ArrayList<>();
    private int totalCount;
}
