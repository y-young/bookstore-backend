package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
