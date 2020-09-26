package com.example.demo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@Getter
//builder를 똑같은 것을 생성할 수 있다.(Instance에 대해서)
@Builder
public class HohoRequest {
    private Integer num;
    private String name;
    private HohoRequest(){

    }
    private HohoRequest(Integer num, String name,Map<String,Boolean>updatedFieldMap){
        this.num = num;
        this.name =name;
        this.updatedFieldMap = updatedFieldMap;
    }
    private Map<String, Boolean> updatedFieldMap = new HashMap<>();
    public void setNum(Integer num){
        this.num = num;
        this.addFieldNameInMap("num");
    }
    public void setName(String name){
        this.name = name;
        this.addFieldNameInMap("name");
    }
    public boolean isNumUpdated(){
        return this.updatedFieldMap.containsKey("num");
    }
    public boolean isNameUpdated(){
        return this.updatedFieldMap.containsKey("name");
    }
    private void addFieldNameInMap(String fieldName) {
        this.updatedFieldMap.put(fieldName, true);
    }

}
