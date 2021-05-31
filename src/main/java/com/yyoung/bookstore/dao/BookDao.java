package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.entity.Book;

import java.util.List;

public interface BookDao {
    Book findById(Integer bookId);

    List<Book> findAll();

    void deductStock(Book book, Integer amount);

    void addOne(Book book);

    void deleteOne(Integer bookId);

    Book updateOne(Book book);

    List<BookSales> getSales();
}
