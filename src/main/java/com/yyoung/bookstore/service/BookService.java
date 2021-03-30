package com.yyoung.bookstore.service;

import com.yyoung.bookstore.entity.Book;

public interface BookService {
    Book findById(Integer bookId);
}
