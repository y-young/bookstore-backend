package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dto.BookSales;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.entity.BookNode;
import com.yyoung.bookstore.entity.Tag;
import com.yyoung.bookstore.exception.ResourceNotFoundException;
import com.yyoung.bookstore.repository.BookNodeRepository;
import com.yyoung.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@CacheConfig(cacheNames = "books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookDaoImpl implements BookDao {
    private final BookRepository bookRepository;
    private final BookNodeRepository bookNodeRepository;

    public Book findById(Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(ResourceNotFoundException::new);
        BookNode bookNode = bookNodeRepository.findById(bookId).orElseGet(() -> new BookNode(book.getId()));
        book.setTags(bookNode.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        return book;
    }

    public List<Book> findByIdIn(List<Integer> bookIds) {
        return bookRepository.findByIdIn(bookIds);
    }

    public Page<Book> findByIdIn(List<Integer> bookIds, Pageable pageable) {
        return bookRepository.findByIdIn(bookIds, pageable);
    }

    public List<Book> findAll() {
        return bookRepository.findByDeletedIsFalse();
    }

    public Page<Book> findAll(Pageable pageable) {
        Page<Book> books = bookRepository.findByDeletedIsFalse(pageable);
        for (Book book :
                books) {
            book.setTags(
                    bookNodeRepository
                            .findById(book.getId())
                            .orElseGet(() -> new BookNode(book.getId()))
                            .getTags()
                            .stream().map(Tag::getName)
                            .collect(Collectors.toList())
            );
        }
        return books;
    }

    public Page<Book> findAll(String keyword, Pageable pageable) {
        return bookRepository.findByTitleContainsAndDeletedIsFalse(keyword, pageable);
    }

    public Book addOne(Book book) {
        BookNode bookNode = new BookNode(book.getId());
        for (String tagName :
                book.getTags()) {
            Tag tag = new Tag(tagName);
            bookNode.hasTag(tag);
        }
        bookNodeRepository.save(bookNode);
        return bookRepository.save(book);
    }

    @CachePut(key = "#book.getId()")
    public Book updateOne(Book book) {
        BookNode bookNode = bookNodeRepository.findById(book.getId()).orElseGet(() -> new BookNode(book.getId()));
        for (String tagName :
                book.getTags()) {
            Tag tag = new Tag(tagName);
            bookNode.hasTag(tag);
        }
        bookNode = bookNodeRepository.save(bookNode);
        System.out.println(bookNode);
        return bookRepository.save(book);
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.REPEATABLE_READ)
    @CacheEvict(key = "#bookId")
    public void reduceStock(Integer bookId, Integer amount) {
        // Avoid direct update on entity, since it'll bypass transaction
        // https://stackoverflow.com/questions/8190926/transactional-saves-without-calling-update-method
        bookRepository.findById(bookId).ifPresent(consumer -> {
            consumer.setStock(consumer.getStock() - amount);
            bookRepository.save(consumer);
        });
    }

    // Only used in batch deletion
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

    public Page<Book> getByRelatedTags(String tag, Pageable pageable) {
        List<BookNode> bookNodes = bookNodeRepository.findBookNodeByRelatedTags(tag);
        return bookRepository.findByIdIn(
                bookNodes.stream().map(BookNode::getId).collect(Collectors.toList()),
                pageable
        );
    }
}
