package com.example.demo;

import com.example.demo.book.model.Person;
import com.example.demo.book.model.Widget;
import com.example.demo.book.repository.PersonRepository;
import com.example.demo.member.repository.MemberRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.operation.MicrosoftSqlDatabaseOperationLookup;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Assert.*;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
public class UserIntegrationTest {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    @Qualifier("userJpaProperties")
    private JpaProperties jpaProperties;
    @Autowired
    private DefaultListableBeanFactory df;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setUp(){
        Person person = Person.builder().name("ds").build();
        Widget w1 = Widget.builder().name("hoho").build();
        Widget w2 = Widget.builder().name("hihi").build();
        w1.setPerson(person);
        w2.setPerson(person);
        personRepository.save(person);
    }
    @Test
    @DatabaseSetup(
            value = {
                    "/database/widget.xml",
                    "/database/person.xml",

            })
    public void givenLazyTest() throws Exception{
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("DS"))
                .andExpect(jsonPath("$.widget..id").isArray())
                .andExpect(jsonPath("$.widget[0].id").value(1))
                .andExpect(jsonPath("$.widget[1].id").value(2))
                .andExpect(jsonPath("$.widget[2].id").value(3))
                .andExpect(jsonPath("$.widget[3].id").value(4))
               // .andExpect(jsonPath("$.widget[?(@ size 4)]").value(true))
                .andDo(print());
//        for(String name : df.getBeanDefinitionNames()){
//            System.out.println(name +"\t"+df.getBeanDefinition(name).getClass().getName());
//        }
        System.out.println(df.getBeanDefinition("userJpaProperties").getPropertyValues());
        System.out.println(jpaProperties.getProperties());
        System.out.println(jpaProperties.getDatabasePlatform());
        Map<String,String> ret = jpaProperties.getProperties();
        ret.keySet().forEach(entity->System.out.println(entity+" "+ret.get(ret)));
    }
    @Test
    @DatabaseSetup(
            value = {
                    "/database/member.xml"
            })
    public void existByIdTest(){
        boolean ret = memberRepository.existByID(1);
        assertTrue(ret);
        boolean ret1 = memberRepository.existByID(1000);
        assertFalse(ret1);
    }
}
