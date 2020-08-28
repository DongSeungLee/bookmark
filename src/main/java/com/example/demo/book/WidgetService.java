package com.example.demo.book;

import com.example.demo.UserConfig;
import com.example.demo.book.model.Person;
import com.example.demo.book.model.Widget;
import com.example.demo.book.repository.PersonRepository;
import com.example.demo.book.repository.WidgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
@ConditionalOnBean(UserConfig.class)
@Service
@Slf4j
public class WidgetService {
    private final WidgetRepository widgetRepository;
    private final PersonRepository personRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    public WidgetService(WidgetRepository widgetRepository,
                         PersonRepository personRepository) {
        this.widgetRepository = widgetRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public List<Widget> findWidgetByName(String name) {
        List<Widget> list = widgetRepository.findByName(name);
        if (list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        list.stream()
                .peek(Widget::manipulateName)
                .map(Widget::getId)
                .forEach(entity -> System.out.println(entity));
        return list;
    }
    @Transactional
    public void initialize() {
        Widget w1 = Widget.builder().name(UUID.randomUUID().toString()).build();
        Widget w2 = Widget.builder().name(UUID.randomUUID().toString()).build();
        Widget w3 = Widget.builder().name(UUID.randomUUID().toString()).build();
        Widget w4 = Widget.builder().name(DATE_FORMATTER.format(LocalDateTime.now())).build();
        Person p1 = Person.builder().name("ds").build();
        personRepository.save(p1);
        w1.setPerson(p1);
        w2.setPerson(p1);
        w3.setPerson(p1);
        w4.setPerson(p1);
        widgetRepository.save(w1);
        widgetRepository.save(w2);
        widgetRepository.save(w3);
        widgetRepository.save(w4);

    }
}
