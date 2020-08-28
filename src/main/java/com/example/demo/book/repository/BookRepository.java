package com.example.demo.book.repository;


import com.example.demo.book.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity,Integer>,BookRepositoryCustom {
    List<BookEntity> findByBookIdInAndActive(List<Integer> ids, Boolean active);
}
