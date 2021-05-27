package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.BookDto;
import com.yyoung.bookstore.dto.UploadResult;
import com.yyoung.bookstore.entity.Book;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Integer bookId);

    void addOne(BookDto newBook);

    void deleteOne(Integer bookId);

    Book updateOne(Integer bookId, BookDto book);

    UploadResult uploadCover(MultipartFile file);

    Resource viewCover(String filename);
}
