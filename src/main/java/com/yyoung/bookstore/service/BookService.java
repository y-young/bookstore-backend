package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.BookDto;
import com.yyoung.bookstore.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Integer bookId);

    void addOne(BookDto newBook);

    void deleteOne(Integer bookId);

    Book updateOne(Integer bookId, BookDto book);
}
