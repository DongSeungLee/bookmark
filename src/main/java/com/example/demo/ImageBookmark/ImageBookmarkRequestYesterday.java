package com.example.demo.ImageBookmark;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ImageBookmarkRequestYesterday extends ImageBookmarkRequest{
    public ImageBookmarkRequestYesterday(){}
    public ImageBookmarkRequestYesterday(String endDateStr){
        this.startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0, 0));
        this.startDateStr = startDate.format(DATE_FORMATTER);
        this.endDateStr = endDateStr;
        if(endDateStr==null){
            this.endDateStr = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(23, 59, 59, 999999999)).format(DATE_FORMATTER);
        }
        this.endDate = LocalDateTime.parse(this.endDateStr);
    }

}
