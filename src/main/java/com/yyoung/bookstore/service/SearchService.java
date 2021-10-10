package com.yyoung.bookstore.service;

import com.yyoung.bookstore.entity.Book;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    void initIndex();

    void reIndex();

    void addToIndex(Book book);

    void updateIndex(Book book);

    void removeFromIndex(Book book);

    void removeFromIndex(List<Book> books);

    Page<Book> search(String query, Pageable pageable) throws SolrServerException, IOException;
}
