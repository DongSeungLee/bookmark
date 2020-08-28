package com.example.demo.book.repository;

import com.example.demo.book.model.Widget;

import java.util.List;

public interface WidgetRepositoryCustom {
    List<Widget> findByName(String name);
}
