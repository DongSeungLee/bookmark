package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
public class WidgetDTO {
    @JsonProperty("WidgetId")
    private Integer widgetId;
    @JsonProperty("WidgetName")
    private String widgetName;
    @JsonProperty("PersonId")
    private Integer personId;
    @JsonProperty("PersonName")
    private String personName;

    @Builder
    public WidgetDTO(Integer widgetId,String widgetName,Integer personId,String personName){
        this.widgetId = widgetId;
        this.widgetName = "hoho"+widgetName;
        this.personId = personId;
        this.personName = personName;
    }
}
