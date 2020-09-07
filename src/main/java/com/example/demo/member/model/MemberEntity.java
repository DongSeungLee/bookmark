package com.example.demo.member.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Member")
@Getter
@Setter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Member_id")
    private Integer memberId;

    @Column(name = "name")
    private String name;

    public MemberEntity() {

    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")//,insertable = false,updatable = false)
    private TeamEntity teamEntity;
    public void setTeamEntity(TeamEntity teamEntity) {
        this.teamEntity = teamEntity;
//        teamEntity.getMembers().add(this);
    }
    //    @Builder
    public MemberEntity(String name) {
        this.name = name;
    }

    // alt + insert => memberId is the primary key and only descriminator!
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberEntity that = (MemberEntity) o;
        return Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, name, teamEntity);
    }

    @Builder
    public MemberEntity(Integer memberId, String name, Integer teamId) {
        this.memberId = memberId;
        this.name = name;
    }

}
