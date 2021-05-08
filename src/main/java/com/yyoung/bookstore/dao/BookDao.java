package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.entity.Book;

import java.util.List;

public interface BookDao {
    Book findById(Integer bookId);

    List<Book> findAll();
}
