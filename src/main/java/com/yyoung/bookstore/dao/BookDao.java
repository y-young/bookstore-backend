package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface BookDao {
    Book findById(Integer bookId);

    Page<Book> findAll(Pageable pageable);

    Page<Book> findAll(String keyword, Pageable pageable);

    void deductStock(Book book, Integer amount);

    void addOne(Book book);

    void deleteOne(Integer bookId);

    Book updateOne(Book book);

    List<BookSales> getSales();

    List<BookSales> getSales(Date start, Date end);
}
