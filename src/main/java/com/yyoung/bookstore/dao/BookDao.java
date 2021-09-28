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

    List<Book> findByIdIn(List<Integer> bookIds);

    void addOne(Book book);

    Book updateOne(Book book);

    void reduceStock(Book book, Integer amount);

    void updateMany(List<Book> books);

    Page<BookSales> getSales(Pageable pageable);

    Page<BookSales> getSales(Date start, Date end, Pageable pageable);

    List<Book> getLatest(Pageable pageable);

    List<Book> getBestSales(Pageable pageable);

    void save(Book book);
}
