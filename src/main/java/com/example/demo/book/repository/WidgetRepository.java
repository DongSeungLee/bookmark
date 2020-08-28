package com.example.demo.book.repository;

import com.example.demo.book.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WidgetRepository extends JpaRepository<Widget,Integer>,WidgetRepositoryCustom {
}
