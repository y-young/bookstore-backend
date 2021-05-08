package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public Book findById(Integer bookId) {
        return bookDao.findById(bookId);
    }

    public List<Book> findAll() {
        return bookDao.findAll();
    }
}
