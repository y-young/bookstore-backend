package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Book findById(Integer bookId) {
        return bookDao.findById(bookId);
    }
}
