package com.example.demo.member.repository;

import com.example.demo.member.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity,Integer>,MemberRepositoryCustom {
}
