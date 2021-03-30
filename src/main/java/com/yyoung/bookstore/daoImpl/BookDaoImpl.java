package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookDaoImpl implements BookDao {
    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
