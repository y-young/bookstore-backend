package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.exception.ResourceNotFoundException;
import com.yyoung.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookDaoImpl implements BookDao {
    private final BookRepository bookRepository;

    public Book findById(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Book> findByIdIn(List<Integer> bookIds) {
        return bookRepository.findByIdIn(bookIds);
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findByDeletedIsFalse(pageable);
    }

    public Page<Book> findAll(String keyword, Pageable pageable) {
        return bookRepository.findByTitleContainsAndDeletedIsFalse(keyword, pageable);
    }

    public void addOne(Book book) {
        bookRepository.save(book);
    }

    public Book updateOne(Book book) {
        return bookRepository.save(book);
    }

    public void updateMany(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public Page<BookSales> getSales(Pageable pageable) {
        return bookRepository.getSales(pageable);
    }

    public Page<BookSales> getSales(Date start, Date end, Pageable pageable) {
        return bookRepository.getSales(start, end, pageable);
    }

    public List<Book> getLatest(Pageable pageable) {
        return bookRepository.findByDeletedIsFalseOrderByIdDesc(pageable);
    }

    public List<Book> getBestSales(Pageable pageable) {
        return bookRepository.getBestSales(pageable);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }
}
