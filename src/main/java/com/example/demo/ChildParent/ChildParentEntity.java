package com.example.demo.ChildParent;

import com.example.demo.annotation.MyAnnotation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ChildParentEntity {
    @MyAnnotation
    private String name;
    @MyAnnotation("guid")
    private String guid;
    @MyAnnotation(name = "parent")
    private String parent;
    private List<ChildParentEntity> children;

    public ChildParentEntity() {

    }

    @Builder
    public ChildParentEntity(String name, String guid, String parent) {
        this.name = name;
        this.guid = guid;
        this.parent = parent;
    }

    public void addEntity(ChildParentEntity entity) {
        if (Objects.isNull(children)) {
            children = new ArrayList<>();
        }
        children.add(entity);
        return;
    }
}
