package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findAll();

    Optional<Book> findById(Integer bookId);
}
