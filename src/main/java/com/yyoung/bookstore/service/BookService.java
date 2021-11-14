package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.BookDto;
import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.dto.UploadResult;
import com.yyoung.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface BookService {
    Page<Book> findAll(Pageable pageable);

    Book findById(Integer bookId);

    void addOne(BookDto newBook);

    void deleteOne(Integer bookId);

    void deleteMany(List<Integer> bookIds);

    Book updateOne(Integer bookId, BookDto book);

    UploadResult uploadCover(MultipartFile file);

    byte[] viewCover(String id);

    Page<BookSales> getSales(Date start, Date end, Pageable pageable);

    List<Book> getLatest();

    List<Book> getBestSales();
}
