package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dto.BookDto;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final ModelMapper modelMapper;

    public Book findById(Integer bookId) {
        return bookDao.findById(bookId);
    }

    public List<Book> findAll() {
        return bookDao.findAll();
    }

    public void addOne(BookDto newBook) {
        Book book = modelMapper.map(newBook, Book.class);
        bookDao.addOne(book);
    }

    public void deleteOne(Integer bookId) {
        bookDao.deleteOne(bookId);
    }

    public Book updateOne(Integer bookId, BookDto book) {
        Book existingBook = bookDao.findById(bookId);
        modelMapper.map(book, existingBook);
        return bookDao.updateOne(existingBook);
    }
}
