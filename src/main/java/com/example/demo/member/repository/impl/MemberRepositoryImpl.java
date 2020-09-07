package com.example.demo.member.repository.impl;

import com.example.demo.JPA.MSSQLServer2012Templates;
import com.example.demo.member.model.*;
import com.example.demo.member.repository.MemberRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.sql.JPASQLQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo.member.model.QMemberEntity.memberEntity;
import static com.querydsl.core.types.dsl.Expressions.simpleTemplate;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;

@Repository("MemberRepositoryImpl")
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @PersistenceContext(unitName = "userUnit")
    EntityManager entityManager;

    private static final String ISNULL_TEMPLATE = "ISNULL({0},{1})";

    private <T> SimpleTemplate<T> isnull(Class<? extends T> cl, Object arg1, Object arg2) {
        return simpleTemplate(cl, ISNULL_TEMPLATE, arg1, arg2);
    }

    private StringTemplate isnullStr(Object arg1, Object arg2) {
        return stringTemplate(ISNULL_TEMPLATE, arg1, arg2);
    }

    @Override
    public List<MemberEntity> findAllById(List<Integer> ids) {
        return new JPAQuery<MemberEntity>(entityManager)
                .select(memberEntity)
                .from(memberEntity)
                .where(memberEntity.memberId.in(ids))
                .fetch();
    }

    @Override
    public boolean existByID(Integer id) {
        QMemberEntity M = new QMemberEntity("M");
        Integer ret = new JPAQuery<MemberEntity>(entityManager)
                .select(M.memberId)
                .from(M)
                .where(M.memberId.eq(id))
                .fetchFirst();
        return ret != null && ret > 0;
    }

    @Override
    public List<MemberDto> findByName(String name) {
        JPASQLQuery<MemberDto> query = new JPASQLQuery(entityManager, new MSSQLServer2012Templates());
        QMemberEntity M = new QMemberEntity("M");
        return query
                .select(Projections.constructor(MemberDto.class,
                        M.memberId,
                        M.name))
                .from(M)
                .where(M.name.eq(name))
                .fetch();
    }

    @Override
    public List<OrderDto> findOrderByMemeberId(Integer memberId, OrderType orderType) {
        JPASQLQuery<MemberDto> query = new JPASQLQuery(entityManager, new MSSQLServer2012Templates());
        QMemberEntity M = new QMemberEntity("M");
        QOrderEntity O = new QOrderEntity("O");
        StringTemplate DEFAULT = stringTemplate("Default");
        return query
                .select(Projections.constructor(OrderDto.class,
                        O.orderId.as("orderId"),
                        O.memberId.as("memberId"),
                        //O.name.as("orderName"),
                        O.name.coalesce("DEFAULT").as("orderName"),
                        M.name.as("memberName"),
                        O.orderType.as("orderType"),
                        O.createdOn.as("creatdOn")
                ))
                .from(M)
                .leftJoin(O).on(M.memberId.eq(O.memberId))
                .where(O.memberId.eq(memberId).and(O.orderType.eq(orderType))
                        .and(O.createdOn.before(LocalDateTime.now())))
                .fetch();

        // with index가 안되는 것은 with가 mssqlserver dialect라서 그렇다.
        //.addJoinFlag(" with(index =NCI_id_type_createdOn)", JoinFlag.Position.BEFORE_CONDITION)
    }

    @Override
    public List<MemberEntity> findAllEntity() {
        QMemberEntity M = new QMemberEntity("M");
        QTeamEntity T = new QTeamEntity("T");
        return new JPAQuery<>(entityManager)
                .select(M)
                .from(M)
                .innerJoin(M.teamEntity, T).fetchJoin()
                .fetch();
    }

}
