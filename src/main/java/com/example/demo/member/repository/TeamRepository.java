package com.example.demo.member.repository;

import com.example.demo.member.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity,Integer> {
}
