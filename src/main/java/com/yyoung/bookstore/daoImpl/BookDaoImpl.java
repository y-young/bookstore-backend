package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.exception.ResourceNotFoundException;
import com.yyoung.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookDaoImpl implements BookDao {
    private final BookRepository bookRepository;

    public Book findById(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void deductStock(Book book, Integer amount) {
        book.setStock(book.getStock() - amount);
        bookRepository.save(book);
    }

    public void addOne(Book book) {
        bookRepository.save(book);
    }

    public void deleteOne(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

    public Book updateOne(Book book) {
        return bookRepository.save(book);
    }

    public List<BookSales> getSales(Optional<Date> start, Optional<Date> end) {
        if (start.isPresent() && end.isPresent()) {
            return bookRepository.getSalesBetween(start.get(), end.get());
        }
        return bookRepository.getSales();
    }
}
