package com.example.demo.ImageBookmark;

import com.example.demo.FashionGo.FashionGoApiConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public abstract  class ImageBookmarkRequest {
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected String startDateStr;
    protected String endDateStr;
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    protected static String buildURL(String... strings) {
        StringBuilder urlBuilder = new StringBuilder();
        Arrays.stream(strings).forEach(urlBuilder::append);
        return urlBuilder.toString();
    }

    public static String deleteEndpoint(Integer buyerId,Integer bookmarkId){
        return buildURL(FashionGoApiConfig.fashionGoApi,"/v1.0/buyers/",
                 buyerId.toString(),
                "/image-bookmarks/",
                bookmarkId.toString() );
    }
    public static String initEndpoint(Integer buyerId){
        return buildURL(FashionGoApiConfig.fashionGoApi, "/v1.0/buyers/",
                buyerId.toString(), "/image-bookmarks/init");
    }
    public String makeEndpoint(Integer buyerId){

        return buildURL(FashionGoApiConfig.fashionGoApi,
                "/v1.0/buyers/", buyerId.toString(),
                "/image-bookmarks?pn="
                , "1", "&ps=", "30", "&startDate=", startDateStr, "&endDate=", endDateStr);
    }
}
