package com.example.demo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@Slf4j
public class FieldExam {
    private Integer f1;
    private Integer f2;
    private Integer f3;
    private Integer f4;
    private Integer f5;
    private Integer f6;
    private Integer f7;
    private Integer f8;
    private Integer f9;
    private Integer f10;
    private Integer f11;
    private Integer f12;
    private final static Integer lastNumber = 12;
    @Getter(AccessLevel.PRIVATE)
    private final Map<Integer, Field> fFieldMap =
            IntStream.rangeClosed(1, lastNumber).boxed().collect(Collectors.toMap(
                    i -> i, i -> {
                        try {
                            Field fField = FieldExam.class.getDeclaredField(String.format("f%d",i));
                            fField.setAccessible(true);
                            return fField;
                        } catch (NoSuchFieldException e) {
                            return null;
                        }
                    }
                    )
            );

    public List<Integer> getList() {
        return IntStream.rangeClosed(1, lastNumber)
                .mapToObj(a -> {
                    try {
                        Field f = fFieldMap.get(a);
                        f.get(this);
                        return f.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .map(obj -> (Integer) obj)
                .collect(Collectors.toList());
    }

    public void setField(List<Integer>list){
        isContinuous(list);
        AtomicInteger aa = new AtomicInteger(1);
        list.forEach(a->{
            try {
                fFieldMap.get(aa.getAndIncrement()).set(this,a);
            } catch (IllegalAccessException e) {
            }
        });
    }
    private void isContinuous(List<Integer>list){
        boolean isContinuous = false;
        for(Integer a :list){
            if(a==null){
                isContinuous = true;
            }
            else if(isContinuous){
                throw new IllegalArgumentException("FieldExam list must be continuous");
            }
        }
    }
}
