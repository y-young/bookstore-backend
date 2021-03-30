package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
