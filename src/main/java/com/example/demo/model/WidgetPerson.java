package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
public class WidgetPerson {
    @JsonProperty("widgetId")
    private Integer widgetId;
    @JsonProperty("personId")
    private Integer personId;
    @JsonProperty("widgetName")
    private String widgetName;
    @JsonProperty("personName")
    private String personName;

    public WidgetPerson(){

    }
    @Builder
    public WidgetPerson(Integer widgetId,Integer personId,String widgetName,String personName){
        this.widgetId = widgetId;
        this.personId = personId;
        this.widgetName = widgetName;
        this.personName = personName;
    }
}

