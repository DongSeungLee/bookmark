package com.example.demo;

import com.example.demo.member.model.MemberDto;
import com.example.demo.member.model.OrderDto;
import com.example.demo.member.model.OrderType;
import com.example.demo.member.model.QMemberEntity;
import com.example.demo.member.repository.MemberRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.operation.MicrosoftSqlDatabaseOperationLookup;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DbUnitConfiguration(
        databaseConnection = {"userDataSource","productDataSource"},
        databaseOperationLookup = MicrosoftSqlDatabaseOperationLookup.class
)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
public class MemberTest {
    @Autowired
    private MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DatabaseSetup(
            value = {
                    "/database/member.xml"
            })
    public void test_find_fetchFirst() {
        QMemberEntity M = new QMemberEntity("M");
        Integer ret = new JPAQuery<>(entityManager)
                .select(M.memberId)
                .from(M)
                .where(M.name.eq("DS1"))
                .fetchFirst();
        System.out.println(ret);
    }

    @Test
    @DatabaseSetup(
            value = {
                    "/database/member.xml"
            })
    public void test_find_entity_when_does_not_exist() {
        QMemberEntity M = new QMemberEntity("M");
        Integer ret = new JPAQuery<>(entityManager)
                .select(M.memberId)
                .from(M)
                .where(M.memberId.eq(9999))
                .fetchFirst();
        System.out.println(ret == null ? true : false);
    }

    @Test
    @DatabaseSetup(
            value = {
                    "/database/member.xml"
            })
    public void test() {
        List<MemberDto> list = memberRepository.findByName("DS1");
        System.out.println(list.size());
    }

    @Test
    @DatabaseSetup(
            value = {
                    "/database/member.xml",
                    "/database/tblorder.xml",
            })
    public void testJoin(){

        List<OrderDto> list = memberRepository.findOrderByMemeberId(1, OrderType.Shippping);
        System.out.println(list.size());
    }
}
