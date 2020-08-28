package com.example.demo;

import com.example.demo.book.WidgetService;
import com.example.demo.book.model.Person;
import com.example.demo.book.model.QPerson;
import com.example.demo.book.model.QWidget;
import com.example.demo.book.model.Widget;
import com.example.demo.book.repository.PersonRepository;
import com.example.demo.book.repository.WidgetRepository;
import com.example.demo.model.NotFoundException;
import com.example.demo.model.ResultCode;
import com.example.demo.model.WidgetDTO;
import com.example.demo.model.WidgetPerson;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.operation.MicrosoftSqlDatabaseOperationLookup;
import com.google.common.collect.Iterators;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.sql.JPASQLQuery;

import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLServer2012Templates;
import com.querydsl.sql.Union;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.querydsl.core.types.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static com.example.demo.book.model.QPerson.person;
import static com.example.demo.book.model.QWidget.widget;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
@DbUnitConfiguration(
        databaseConnection = {"userDataSource"},
        databaseOperationLookup = MicrosoftSqlDatabaseOperationLookup.class
)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
public class toBuilerTest {

    @Value("${propertyTest.value}")
    private String propertTest;
    @Value("${testValue}")
    private Integer testValue;
    @Autowired
    WidgetService widgetService;
    @Autowired
    WidgetRepository widgetRepository;
    @Autowired
    PersonRepository personRepository;
    @PersistenceContext(unitName = "userUnit")
    EntityManager entityManager;

    @Autowired
    @Qualifier("sqlServerFunction")
    QueryDSLSQLFunctions queryDSLSQLFunctions;

    @Autowired
    @Qualifier("hibernateSessionFactory")
    private LocalSessionFactoryBean localSessionFactoryBean;

    private static class MEMBER_PERSON_PATH {
        public static NumberPath<Integer> widgetId(Path<?> parent) {
            return Expressions.numberPath(Integer.class, parent, "widgetId");
        }

        public static NumberPath<Integer> personId(Path<?> parent) {
            return Expressions.numberPath(Integer.class, parent, "personId");
        }

        public static StringPath widgetName(Path<?> parent) {
            return Expressions.stringPath(parent, "widgetName");
        }

        public static StringPath personName(Path<?> parent) {
            return Expressions.stringPath(parent, "personName");
        }
    }

    @Test
    public void toBuilderTest() {
        Widget widget = Widget.builder()
                .name("hoho")
                .id(1)
                .build();
        Widget.WidgetBuilder widgetBuilder = widget.toBuilder();
        Widget widget1 = widgetBuilder.name("hehe").build();
        assertThat(widget.getId()).isEqualTo(widget1.getId());
        System.out.println(widget.toBuilder().toString());
    }

    @Test
    public void testValue() {
        System.out.println(propertTest);
        System.out.println(testValue);
    }

    @Test
    @Transactional("userTransactionManager")
    @DatabaseSetup("/database/widget.xml")
    public void testUniqueName() {

        Widget w1 = Widget.builder().name("a").build();

        log.info("testUniqueName Transactional : {}", TransactionSynchronizationManager.isActualTransactionActive());
        widgetRepository.save(w1);
        String sql = "SELECT * FROM WIDGET";
        Query nativeQuery = entityManager.createNativeQuery(sql, Widget.class);
        List<Widget> list = nativeQuery.getResultList();
        System.out.println(list.size());


    }

    @Test
    @Transactional("userTransactionManager")
    @DatabaseSetup(
            value = {"/database/person.xml",
                    "/database/widget.xml",

            })
    public void testFindwidget() {
//        List<Widget> list = widgetRepository.findAll();
//        Widget w1 = entityManager.find(Widget.class,1);
//        assertThat(list.size()).isEqualTo(8);
//        System.out.println(list.get(0).equals(w1));
//        String sql = "UPDATE Widget w set w.name = 'hoho'";
//        entityManager.createQuery(sql).executeUpdate();
//        list = widgetRepository.findAll();
        SimplePath<Widget> W = Expressions.path(Widget.class, "Widget");
        SimplePath<Person> P = Expressions.path(Person.class, "Person");
        NumberPath<Integer> widgetId = Expressions.numberPath(Integer.class, W, "id");
        NumberPath<Integer> personId = Expressions.numberPath(Integer.class, P, "name");
        StringPath widgetName = Expressions.stringPath(W, "id");
        StringPath personName = Expressions.stringPath(P, "name");
        System.out.println(widget.id.toString() + widgetId.toString());
        QWidget qWidget = new QWidget("Widget");

        List<WidgetDTO> list = new JPAQuery<>(entityManager)
                .select(Projections.constructor(WidgetDTO.class,
                        widget.id,
                        widget.name,
                        person.id,
                        person.name
                ))
                .from(widget)
                .innerJoin(widget.person, person)
                .where(widget.person.id.in(Arrays.asList(1, 2)))
                .fetch();
        System.out.println(list);
        List<Widget> ret = new JPAQuery<Widget>(entityManager)
                .select(widget)
                .from(widget)
                .innerJoin(widget.person, person)
                .fetch();
//        System.out.println(ret.get(0).getPerson());
//        System.out.println(ret.get(1).getPerson());
//        System.out.println(ret.get(2).getPerson());
//        System.out.println(ret.get(3).getPerson());
//        System.out.println(ret.get(4).getPerson());
//        System.out.println(ret.get(5).getPerson());
//        System.out.println(ret.get(6).getPerson());
//        System.out.println(ret.get(7).getPerson());
    }

    @Test
    @Transactional("userTransactionManager")
    @DatabaseSetup(
            value = {
                    "/database/widget.xml",
                    "/database/person.xml",

            })
    public void testFindByName() {

        List<Widget> list = widgetRepository.findAll().stream().filter(entity -> Objects.isNull(entity.getPerson()))
                .collect(Collectors.toList());
        List<Integer> pids = Arrays.asList(1, 2);
        List<Person> plist = personRepository.findAllById(pids);
        list.forEach(entity -> {
            if (entity.getId() % 2 == 0) {
                entity.setPerson(plist.get(0));
            } else {
                entity.setPerson(plist.get(1));
            }
        });
        widgetRepository.saveAll(list);
        list = widgetRepository.findAll().stream().filter(entity -> Objects.isNull(entity.getPerson())).collect(Collectors.toList());
        assertThat(list.size()).isZero();
        Person p1 = personRepository.findById(1).orElseThrow(() -> new NotFoundException("hooh", ResultCode.NOT_FOUND));
        System.out.println(p1.getWidget().size());

        log.info("testFindByName trasaction : {}", TransactionSynchronizationManager.isActualTransactionActive());
        Person p2 = findByPersonId(2);
        assertThat(p2.getWidget()).isNotNull();
        p2.getWidget().forEach(entity -> assertThat(entity.getId()).isNotNull());
    }

    private Person findByPersonId(Integer id) {
        return new JPAQuery<Person>(entityManager)
                .select(person)
                .from(person)
                .innerJoin(person.widget, widget).fetchJoin()
                .where(person.id.eq(id))
                .fetchOne();
    }

    @Test
    @Transactional("userTransactionManager")
    @DatabaseSetup(
            value = {
                    "/database/widget.xml",
                    "/database/person.xml",

            })
    public void testcriteria() {
//        String jpql = "select w from Widget as w inner join w.person p where p.name = :personname";
//        List<Widget> resultList = entityManager.createQuery(jpql,Widget.class)
//                .setParameter("personname","DS")
//                .getResultList();
        //ManyToOne
        String jpql = "select w from Widget as w join fetch w.person";
        List<Widget> resultList = entityManager.createQuery(jpql, Widget.class)
                .getResultList();
        System.out.println(resultList.size());
        //OneToMany
        String jpql1 = "select distinct p from Person as p join fetch p.widget";
        List<Person> resultList1 = entityManager.createQuery(jpql1, Person.class)
                .getResultList();
        System.out.println(resultList1.size());

        Person p1 = findWidgetByPersonId(1);
        System.out.println(p1);
        System.out.println(p1.equals(resultList1.get(0)));

        Widget w1 = findByName("2020-06-27T21:47:04.104000");
        System.out.println(w1);
    }

    private Person findWidgetByPersonId(Integer id) {
        return new JPAQuery<Person>(entityManager)
                .select(person)
                .from(person)
                .innerJoin(person.widget, widget).fetchJoin()
                .where(person.id.eq(id))
                .fetchOne();
    }

    private Widget findByName(String name) {
        return new JPAQuery<Widget>(entityManager)
                .select(widget)
                .from(widget)
                .where(widget.name.eq(name))
                .fetchOne();
    }

    @Test
    @Transactional("userTransactionManager")
    @DatabaseSetup(
            value = {
                    "/database/widget.xml",
                    "/database/person.xml",

            })
    public void testSession() {
        Widget w1 = Widget.builder().name("ds").build();
        Person p1 = Person.builder().name("hoho").build();
        w1.setPerson(p1);

        Session session = localSessionFactoryBean.getConfiguration().buildSessionFactory().getCurrentSession();
        session.save(p1);
        session.save(w1);
        Widget ret = findByName(w1.getName());
        assertThat(ret).isNotNull();
        assertThat(ret.getName()).isEqualTo("ds");
        List<Widget> ww = widgetRepository.findAll();
        System.out.println(ww.size());
    }

    @Ignore
    @Test
    @Transactional("userTransactionManager")
    @DatabaseSetup(
            value = {
                    "/database/widget.xml",
                    "/database/person.xml",

            })
    public void testGroupBy() {
        QWidget qWidget = QWidget.widget;
        QPerson qPerson = QPerson.person;
        SimplePath<Object> temp = Expressions.path(Object.class, "WidgetPerson");
        NumberPath<Integer> path_widgetId = MEMBER_PERSON_PATH.widgetId(temp);
        NumberPath<Integer> path_personId = MEMBER_PERSON_PATH.personId(temp);
        StringPath path_widgetName = MEMBER_PERSON_PATH.widgetName(temp);
        StringPath path_personName = MEMBER_PERSON_PATH.personName(temp);
        NumberExpression<Integer> maxInt = Expressions
                .cases()
                .when(qWidget.id.goe(qPerson.id)).then(qWidget.id)
                .otherwise(qPerson.id);
        NumberExpression<Integer> sumInt = qPerson.id.add(qWidget.id);

//        List<WidgetPerson> list = new JPAQuery<>(entityManager)
//                .select(Projections.constructor(
//                        WidgetPerson.class,
//                        sumInt,
//                        maxInt,
//                        Expressions.stringTemplate("function('concat',{0},{1},{2})",
//                                qWidget.name, " !+! ", qPerson.name.coalesce("null!!!")),
//                        qPerson.name.coalesce("hohoisNULL!!")
//                ))
//                .from(qWidget)
//                .where(qPerson.id.in(Arrays.asList(1, 2)))
//                .leftJoin(qWidget.person, person)
//                .fetch();
//        System.out.println(list);


        JPASQLQuery<WidgetPerson> jpasqlQuery = new JPASQLQuery(entityManager,new SQLServer2012Templates());
        jpasqlQuery
        .select(
                Projections.constructor(
                        WidgetPerson.class,
                        path_widgetId,
                        path_personId,
                        path_widgetName,
                        path_personName
                )
        )
                .from(subquery(),temp)
                .fetch();
        List<WidgetPerson>list = jpasqlQuery.fetch();
        System.out.println(list);
    }

    private JPASQLQuery subquery(){
        JPASQLQuery jpasqlQuery = new JPASQLQuery(entityManager,new SQLServer2012Templates());
        QWidget qWidget = QWidget.widget;
        jpasqlQuery
                .select(qWidget.id,qWidget.id.as("personId"),qWidget.name,qWidget.name.as("personName"))
                .from(qWidget);
        return jpasqlQuery;
    }
    @Test
    public void testtest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        System.out.println(Iterators.getLast(list.iterator()));
        Widget widget = Widget.builder().name("hoho").build();
        list.stream().filter(a -> a % 4 == 0).findFirst().map(entity -> {
            widget.setId(entity);
            return entity;
        });
        System.out.println(widget.getId());
    }

    @Test
    @Transactional("userTransactionManager")
    @DatabaseSetup(
            value = {
                    "/database/widget.xml",
                    "/database/person.xml",
            })
    public void testUnion(){
        JPASQLQuery jpasqlQuery1 = new JPASQLQuery(entityManager,new SQLServer2012Templates());
        JPASQLQuery jpasqlQuery2 = new JPASQLQuery(entityManager,new SQLServer2012Templates());

        QWidget qWidget1 = new QWidget("widget1");
        QWidget qWidget2 = new QWidget("widget2");

        jpasqlQuery1.select(qWidget1.id,qWidget1.name).from(qWidget1).where(qWidget1.id.eq(1));
        jpasqlQuery2.select(qWidget2.id,qWidget2.name).from(qWidget2).where(qWidget2.id.eq(2));


        Union unionQuery = SQLExpressions.unionAll(jpasqlQuery1,jpasqlQuery2);
        JPASQLQuery<Widget> jpasqlQuery3 = new JPASQLQuery(entityManager,new SQLServer2012Templates());
        SimplePath<Object> Widget = Expressions.path(Object.class, "widget");
        NumberPath<Integer> path_widgetId = Expressions.numberPath(Integer.class,Widget,"id");
        NumberPath<Integer> path_personId = Expressions.numberPath(Integer.class,Widget,"personId");
        StringPath path_widgetName = Expressions.stringPath(Widget,"name");
        StringPath path_personName = Expressions.stringPath(Widget,"personName");


        List<WidgetPerson>list =
                jpasqlQuery3.select(
                        Projections.constructor(
                                WidgetPerson.class,
                                path_widgetId.as("widgetId"),
                                path_widgetId.add(Expressions.TWO).as("personId"),
                                path_widgetName.as("widgetName"),
                                path_widgetName.as("personName")
                                )
                )
                        .from(unionQuery,Widget).fetch();
        System.out.println(list);

    }
}
