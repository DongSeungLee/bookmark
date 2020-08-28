package com.example.demo.book.repository;

import com.example.demo.book.model.Widget;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.example.demo.book.model.QWidget.widget;
@Repository
public class WidgetRepositoryImpl implements WidgetRepositoryCustom{
    @PersistenceContext(unitName = "userUnit")
    EntityManager entityManager;
    @Override
    public List<Widget> findByName(String name) {
        return new JPAQuery<Widget>(entityManager)
                .select(widget)
                .from(widget)
                .where(widget.name.eq(name))
                .fetch();
    }
}
