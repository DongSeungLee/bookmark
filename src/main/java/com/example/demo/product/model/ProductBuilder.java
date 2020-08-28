package com.example.demo.product.model;

import lombok.Getter;

@Getter
public class ProductBuilder {
    private Integer id;
    private String name;
    public ProductBuilder(){

    }
    private ProductBuilder(Integer id,String name){
        this.id = id;
        this.name = name;
    }
    public static class Builder{
        private Integer id;
        private String name;

        public ProductBuilder build(){
            return new ProductBuilder(this.id,this.name);
        }
        public Builder id(Integer id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }

    }
    public static ProductBuilder.Builder builder(){
        return new ProductBuilder.Builder();
    }
}


