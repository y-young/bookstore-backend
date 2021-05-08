package com.yyoung.bookstore.service;

import com.yyoung.bookstore.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Integer bookId);
}
