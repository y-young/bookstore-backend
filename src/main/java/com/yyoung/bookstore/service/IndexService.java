package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.IndexUpdateRequest;
import com.yyoung.bookstore.entity.Book;

public interface IndexService {

    void onMessage(IndexUpdateRequest request);

    void init();

    void refresh();

    void add(Book book);

    void update(Book book);

    void remove(Book book);
}
