package com.example.demo.book;

import com.example.demo.book.model.Person;
import com.example.demo.book.repository.PersonRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.example.demo.book.model.QWidget.widget;
import static com.example.demo.book.model.QPerson.person;
@Service
public class PersonService {
    private final PersonRepository personRepository;

    @PersistenceContext(unitName = "userUnit")
    EntityManager entityManager;
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(value = "userTransactionManager", readOnly = true)
    public Optional<Person> findOne(Integer id) {
        return Optional.ofNullable(findWidgetById(id));
    }
    private Person findWidgetById(Integer id){
        return new JPAQuery<Person>(entityManager)
                .select(person)
                .from(person)
                .leftJoin(person.widget,widget).fetchJoin()
                .where(person.id.eq(id))
                .fetchOne();
    }

}
