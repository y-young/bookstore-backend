package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.entity.Book;

public interface BookDao {
    Book findById(Integer bookId);
}
