package com.example.demo.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllDto {
    private Integer memberId;
    private String memberName;
    private Integer bookId;
    private String bookName;
    private Integer personId;
    private String personName;
    private Integer productId;
    private String productName;
    private Double price;
    @Builder
    public AllDto(Integer memberId,String membername, Integer bookId,String bookName,
                  Integer personId,String personname,Integer productId, String productName,
                  Double price){
        this.memberId = memberId;
        this.memberName = membername;
        this.bookId = bookId;
        this.bookName = bookName;
        this.personId = personId;
        this.personName = personname;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }
}
