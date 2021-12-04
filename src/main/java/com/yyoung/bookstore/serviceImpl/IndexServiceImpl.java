package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.configuration.SolrConfiguration;
import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dto.IndexUpdateRequest;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexServiceImpl implements IndexService {
    private final SolrConfiguration solrConfiguration;

    private final BookDao bookDao;

    private SolrClient getClient() {
        if(solrConfiguration.getCloud()) {
            return new CloudSolrClient.Builder(Collections.singletonList(solrConfiguration.getUrl())).build();
        }
        return new HttpSolrClient.Builder(solrConfiguration.getUrl()).build();
    }

    @JmsListener(destination = "updateIndex")
    public void onMessage(IndexUpdateRequest request) {
        switch (request.getAction()) {
            case init:
                init();
                break;
            case refresh:
                refresh();
            case add:
                add(request.getBook());
                break;
            case update:
                update(request.getBook());
                break;
            case remove:
                remove(request.getBook());
                break;
        }
    }

    public void init() {
        final SolrClient client = getClient();
        final String collection = solrConfiguration.getCollection();
        log.info("Building index...");
        bookDao.findAll().forEach(book -> {
            try {
                client.addBean(collection, book);
                client.commit(collection);
            } catch (IOException | SolrServerException e) {
                e.printStackTrace();
            }
        });
        log.info("Successfully built index");
    }

    public void refresh() {
        final SolrClient client = getClient();
        final String collection = solrConfiguration.getCollection();
        log.info("Dropping index...");
        try {
            client.deleteByQuery(collection, "*:*");
            client.commit(collection);
            init();
        } catch (SolrServerException | IOException exception) {
            log.warn("Failed to drop index:", exception);
        }
        log.info("Successfully refreshed index");
    }

    public void add(Book book) {
        if (book == null) {
            return;
        }
        final SolrClient client = getClient();
        final String collection = solrConfiguration.getCollection();
        try {
            client.addBean(collection, book);
            client.commit(collection);
        } catch (SolrServerException | IOException exception) {
            log.warn("Failed to add book to index:", exception);
        }
        log.info("Successfully added book {} to index", book.getId());
    }

    public void update(Book book) {
        if (book == null) {
            return;
        }
        remove(book);
        add(book);
    }

    public void remove(Book book) {
        if (book == null) {
            return;
        }
        final SolrClient client = getClient();
        final String collection = solrConfiguration.getCollection();
        try {
            client.deleteById(collection, book.getId().toString());
            client.commit(collection);
        } catch (SolrServerException | IOException exception) {
            log.warn("Failed to remove book from index:", exception);
        }
        log.info("Successfully removed book {} from index", book.getId());
    }

    public void removeMany(List<Book> books) {
        if (books == null) {
            return;
        }
        final SolrClient client = getClient();
        final String collection = solrConfiguration.getCollection();
        try {
            for (Book book : books) {
                client.deleteById(collection, book.getId().toString());
            }
            client.commit(collection);
        } catch (SolrServerException | IOException exception) {
            log.warn("Failed to remove book from index:", exception);
        }
        log.info("Successfully removed books {} to index", books);
    }
}
