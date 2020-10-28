package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum VendorIndustryType {
    
    Women(1, "Women's Clothing"),
    Men(2, "Man's Clothing"),
    Kids(3, "Children's Clothing"),
    Accessories(4, "Accessories"),
    Shoes(5, "Shoes"),
    Others(6, "Others"),
    Handbags(7, "Handbag"),
    Beauty(8, "Beauty");
    private int num;
    private String name;

    VendorIndustryType(int value, String name) {
        this.num = value;
        this.name = name;
    }

    public Integer getNum(){
        return this.num;
    }
    public String getName(){
        return this.getName();
    }
    private static Map<Integer, String> numNameMap = new HashMap<>();
    private static Map<String, Integer> nameNumMap = new HashMap<>();

    static {
        for (VendorIndustryType a : VendorIndustryType.values()) {
            numNameMap.put(a.num, a.name);
            nameNumMap.put(a.name, a.num);
        }
    }

    public static Integer getNum(String _name){
        return Optional.ofNullable(nameNumMap.get(_name)).orElseThrow(()->new IllegalArgumentException("cannot find name :"+_name));
    }
    public static String getName(Integer _num){
        return Optional.ofNullable(numNameMap.get(_num)).orElseThrow(()->new IllegalArgumentException("cannot find num :"+_num));
    }
}
