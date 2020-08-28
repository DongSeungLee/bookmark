package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImageUrls{
    private String originalImageUrl;
    private String thumbnailUrl;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String crossOriginImageUrl;
    private Long bookmarkId;
    private String createdOn;


}
