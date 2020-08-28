package com.example.demo.request;

import com.example.demo.member.model.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateMemberRequest {

    private Integer memberId;


    private String name;


    private String sex;


    private String phoneNumber;

    public static MemberEntity createMemberEntity(CreateMemberRequest request){
        return MemberEntity.builder()
                .name(request.getName())
                .build();
    }
    public CreateMemberRequest(){

    }
    @Builder
    public CreateMemberRequest(Integer memberId, String name, String sex, String phoneNumber){
        this.memberId = memberId;
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
    }
}
