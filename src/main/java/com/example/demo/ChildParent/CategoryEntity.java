package com.example.demo.ChildParent;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryEntity {
    private Integer id;
    private String name;
    private List<CategoryEntity> children = new ArrayList<>();

    public CategoryEntity() {

    }

    @Builder
    public CategoryEntity(Integer id, String name) {
        this.id = id;
        this.name = name;

    }

    public void addChildren(CategoryEntity c) {
        children.add(c);
    }
    public Integer compareCharSum(){
        Integer ret = 0;
        byte[] list = this.name.getBytes();
        for(int i=0;i<list.length;i++){
            ret += list[i];
        }
        return ret;
    }
}
