package com.yyoung.bookstore.search.serviceImpl;

import com.yyoung.bookstore.search.entity.Book;
import com.yyoung.bookstore.search.repository.BookRepository;
import com.yyoung.bookstore.search.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public String findAuthorByTitle(String title) {
        Book book = bookRepository.findByTitle(title);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "资源未找到");
        }
        return book.getAuthor();
    }

}
