package com.example.demo.member.repository.impl;

import com.example.demo.JPA.MSSQLServer2012Templates;
import com.example.demo.book.model.QBookEntity;
import com.example.demo.book.model.QPerson;
import com.example.demo.member.H2SQLServerTemplate;
import com.example.demo.member.model.AllDto;
import com.example.demo.member.model.MemberDto;
import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.model.OrderDto;
import com.example.demo.member.model.OrderType;
import com.example.demo.member.model.QMemberEntity;
import com.example.demo.member.model.QOrderEntity;
import com.example.demo.member.model.QTeamEntity;
import com.example.demo.member.repository.MemberRepositoryCustom;
import com.example.demo.product.model.ProductEntity;
import com.example.demo.product.model.QProductEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.HSQLDBTemplates;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.SQLTemplates;
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

    @Override
    public AllDto findAllEntityById(Integer mid) {
        // HSQLTemplate로 하면 안되고 MSSQLServer2012Templates로 해야 "Member" table로 인식하지 않아서 된다.
        // oracle template도 됨.
        // MySQLTemplates도 됨.
        // H2Templates 이것이 H2 template이고 'HSQLDBTemplates'이건 이상한 것이다.....
        JPASQLQuery<AllDto> jpasqlQuery = new JPASQLQuery(entityManager, new H2Templates());
//        SimplePath<Object> VCP = Expressions.path(Object.class, "VCP");
//        NumberPath<Integer> memberId = Expressions.numberPath(Integer.class, VCP, "memberId");
//        StringPath memberName = Expressions.stringPath(VCP, "memberName");
//        NumberPath<Integer> bookId = Expressions.numberPath(Integer.class, VCP, "bookId");
//        StringPath bookName = Expressions.stringPath(VCP, "memberName");
//        NumberPath<Integer> personId = Expressions.numberPath(Integer.class, VCP, "personId");
//        StringPath personName = Expressions.stringPath(VCP, "memberName");
//        NumberPath<Integer> productId = Expressions.numberPath(Integer.class, VCP, "productId");
//        StringPath productName = Expressions.stringPath(VCP, "memberName");

        SimplePath<Object> product = Expressions.path(Object.class, "product");
        NumberPath<Integer> id = Expressions.numberPath(Integer.class, product, "id");
        StringPath name = Expressions.stringPath(product, "name");
        NumberPath<Double> price = Expressions.numberPath(Double.class, product, "price");
        QMemberEntity M = new QMemberEntity("M");
        QBookEntity B = new QBookEntity("B");
        QPerson P = new QPerson("P");
        QTeamEntity T = new QTeamEntity("T");

        jpasqlQuery.select(
                Projections.constructor(
                        AllDto.class,
                        M.memberId.as("memberId"),
                        M.name.as("memberName"),
                        B.bookId.as("bookId"),
                        B.name.as("bookName"),
                        P.id.as("personId"),
                        P.name.as("personName"),
                        id.as("productId"),
                        name.as("productName")
                )
        ).from(V(mid), product)
                //.leftJoin(M.teamEntity,T)//.on(id.eq(M.memberId))
                .leftJoin(B).on(id.eq(B.bookId))
                .leftJoin(M).on(id.eq(M.memberId))
                .leftJoin(T).on(id.eq(T.teamId))
                .leftJoin(P).on(id.eq(P.id));

        return jpasqlQuery.fetchOne();
    }

    private JPASQLQuery V(Integer mid) {
        JPASQLQuery jpasqlQuery = new JPASQLQuery(entityManager, new H2Templates());
        QProductEntity P = new QProductEntity("P");
        QMemberEntity M = new QMemberEntity("M");
        jpasqlQuery
                .select(P.id,P.name,P.price)
                .from(P)
                .where(P.id.eq(mid));
        JPASQLQuery jpasqlQuery1 = new JPASQLQuery(entityManager, new H2Templates());
        jpasqlQuery1
                .select(M)
                .from(M)
                .where(M.memberId.eq(mid));
        // sqlquery로 가지고 오면 안된다 "Member" table로 인식한다.
        List<MemberEntity> list = jpasqlQuery1.fetch();
        // JPAQuery로는 가져올 수 있는데
//        list = new JPAQuery<>(entityManager)
//                .select(M)
//                .from(M)
//                .where(M.memberId.eq(mid)).fetch();
        return jpasqlQuery;
    }
}
