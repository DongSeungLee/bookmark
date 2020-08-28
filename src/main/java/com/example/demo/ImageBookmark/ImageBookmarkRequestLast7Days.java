package com.example.demo.ImageBookmark;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ImageBookmarkRequestLast7Days extends ImageBookmarkRequest{
    public ImageBookmarkRequestLast7Days(){}
    public ImageBookmarkRequestLast7Days(String endDateStr){
        this.startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(0, 0, 0, 0));
        this.startDateStr = startDate.format(DATE_FORMATTER);
        this.endDateStr = endDateStr;
        if(this.endDateStr==null){
            this.endDateStr = LocalDateTime.now().format(DATE_FORMATTER);
        }
        this.endDate = LocalDateTime.parse(this.endDateStr);
    }

}
