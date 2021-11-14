package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.BookCover;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookCoverRepository extends MongoRepository<BookCover, String> {
}
