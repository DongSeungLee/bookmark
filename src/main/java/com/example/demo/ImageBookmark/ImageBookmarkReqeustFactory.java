package com.example.demo.ImageBookmark;

public class ImageBookmarkReqeustFactory {
    public static ImageBookmarkRequest getImageBookmarkRequest(Integer category, String endDateStr){
        if(category==null || category>3||category<0){
            throw new IllegalArgumentException("invalid category!");
        }
        if(category==0){
            return new ImageBookmarkRequestToday(endDateStr);
        }
        else if(category == 1){
            return new ImageBookmarkRequestYesterday(endDateStr);
        }
        else {
            return new ImageBookmarkRequestLast7Days(endDateStr);
        }
    }
}
