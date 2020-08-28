package com.example.demo.ImageBookmark;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ImageBookmarkRequestToday extends ImageBookmarkRequest{
    public ImageBookmarkRequestToday(){}
    public ImageBookmarkRequestToday(String endDateStr){
       //this.startDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0, 0));
        this.startDate = LocalDateTime.of(LocalDate.now().minusYears(1), LocalTime.of(0, 0, 0, 0));
        this.startDateStr = startDate.format(DATE_FORMATTER);
        this.endDateStr = endDateStr;
        if(this.endDateStr == null){
            this.endDateStr = LocalDateTime.now().format(DATE_FORMATTER);
        }
        this.endDate = LocalDateTime.parse(this.endDateStr);
    }

}
