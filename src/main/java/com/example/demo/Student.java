package com.example.demo;

import lombok.Builder;

public class Student implements Comparable<Student> {
    private Integer id;
    private String name;
    private Boolean isActive;

    @Builder(toBuilder = true)
    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(Student o) {
        int ret = this.id - o.getId();
        // id가 동일하면 name으로 역순!
        if (ret == 0) return o.getName().compareTo(this.name);
        return ret;
    }

    public Boolean getActive1() {
        return this.isActive;
    }

    public void setActive2(Boolean active) {
        this.isActive = active;
    }
}
