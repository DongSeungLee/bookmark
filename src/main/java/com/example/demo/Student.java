package com.example.demo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student implements Comparable<Student> {
    private Integer id;
    private String name;

    @Builder(toBuilder = true)
    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Student o) {
        int ret = this.id - o.getId();
        // id가 동일하면 name으로 역순!
        if (ret == 0) return o.getName().compareTo(this.name);
        return ret;
    }
}
