package com.example.demo.member.repository;

import com.example.demo.member.model.MemberDto;
import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.model.OrderDto;
import com.example.demo.member.model.OrderType;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberEntity> findAllById(List<Integer> ids);

    boolean existByID(Integer id);

    List<MemberDto> findByName(String name);

    List<OrderDto>findOrderByMemeberId(Integer memberId, OrderType orderType);
}
