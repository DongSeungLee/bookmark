package com.example.demo.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "team")
@Getter
@Setter
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "name")
    private String name;

//    @OneToMany(fetch=FetchType.LAZY,mappedBy = "teamEntity")
//    private List<MemberEntity> members = new ArrayList<>();

    public TeamEntity() {

    }

    @Builder
    public TeamEntity(String name) {
        this.name = name;
    }
}
