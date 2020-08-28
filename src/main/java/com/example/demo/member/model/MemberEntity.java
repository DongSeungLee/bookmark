package com.example.demo.member.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Member")
@Getter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Member_id")

    private Integer memberId;

    @Column(name = "name")
    @Setter
    private String name;

    public MemberEntity() {

    }
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "team_id")//,insertable = false,updatable = false)
    private TeamEntity teamEntity;

    public void setTeamEntity(TeamEntity teamEntity){
        this.teamEntity = teamEntity;
        teamEntity.getMembers().add(this);
    }

//    @Builder
    public MemberEntity(String name) {
        this.name = name;
    }
    @Builder
    public MemberEntity(Integer memberId,String name,Integer teamId){
        this.memberId = memberId;
        this.name = name;

    }
}
