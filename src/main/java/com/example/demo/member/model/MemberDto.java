package com.example.demo.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Integer memberId;
    private String name;

    @Builder
    public MemberDto(Integer memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public MemberDto() {

    }
}
