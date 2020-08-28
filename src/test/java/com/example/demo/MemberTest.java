package com.example.demo;

import com.example.demo.member.model.MemberDto;
import com.example.demo.member.model.OrderDto;
import com.example.demo.member.model.OrderType;
import com.example.demo.member.repository.MemberRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.operation.MicrosoftSqlDatabaseOperationLookup;
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
    @Test
    @DatabaseSetup(
            value = {
                    "/database/member.xml"
            })
    public void test(){
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
