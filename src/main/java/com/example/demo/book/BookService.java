package com.example.demo.book;

import com.example.demo.ProductConfig;
import com.example.demo.UserConfig;
import com.example.demo.book.model.BookEntity;
import com.example.demo.book.repository.BookRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.List;
@ConditionalOnBean(UserConfig.class)
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public void addBook(){
        bookRepository.save(BookEntity.builder().name("hoho1").build());
        bookRepository.save(BookEntity.builder().name("hoho2").build());
        bookRepository.save(BookEntity.builder().name("hoho3").build());
        bookRepository.save(BookEntity.builder().name("hoho4").build());
        bookRepository.save(BookEntity.builder().name("hoho5").build());
    }
    public void findBook(){
        List<BookEntity> list = bookRepository.findAll();
//        list.forEach(entity->{
//            System.out.println(entity.toString());
//        });
        return;
    }
    public void findByIdAndActive(List<Integer>ids){
        List<BookEntity>list = bookRepository.findByBookIdInAndActive(ids,true);
        list.forEach(entity->System.out.println(entity.builder().name(entity.getName()).active(entity.getActive()).toString()));
        return;
    }
}
