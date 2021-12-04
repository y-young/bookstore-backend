package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.configuration.SolrConfiguration;
import com.yyoung.bookstore.constants.IndexAction;
import com.yyoung.bookstore.dao.BookDao;
import com.yyoung.bookstore.dto.IndexUpdateRequest;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchServiceImpl implements SearchService {
    private final SolrConfiguration solrConfiguration;

    private final BookDao bookDao;

    private final JmsTemplate jmsTemplate;
    private final String jmsTopic = "updateIndex";

    private SolrClient getClient() {
        if(solrConfiguration.getCloud()) {
            return new CloudSolrClient.Builder(Collections.singletonList(solrConfiguration.getUrl())).build();
        }
        return new HttpSolrClient.Builder(solrConfiguration.getUrl()).build();
    }

    public Page<Book> search(String query, Pageable pageable) throws SolrServerException, IOException {
        final SolrClient client = getClient();

        final Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("q", query);
        queryParamMap.put("fl", "id");
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        final QueryResponse response = client.query("books", queryParams);
        final SolrDocumentList documents = response.getResults();
        List<Integer> bookIds = documents.stream()
                .map(document -> Integer.parseInt((String) document.get("id")))
                .collect(Collectors.toList());
        return bookDao.findByIdIn(bookIds, pageable);
    }

    public void initIndex() {
        jmsTemplate.convertAndSend(jmsTopic, new IndexUpdateRequest(IndexAction.init));
    }

    public void reIndex() {
        jmsTemplate.convertAndSend(jmsTopic, new IndexUpdateRequest(IndexAction.refresh));
    }

    public void addToIndex(Book book) {
        jmsTemplate.convertAndSend(jmsTopic, new IndexUpdateRequest(IndexAction.add, book));
    }

    public void updateIndex(Book book) {
        jmsTemplate.convertAndSend(jmsTopic, new IndexUpdateRequest(IndexAction.update, book));
    }

    public void removeFromIndex(Book book) {
        jmsTemplate.convertAndSend(jmsTopic, new IndexUpdateRequest(IndexAction.remove, book));
    }

    public void removeFromIndex(List<Book> books) {
        jmsTemplate.convertAndSend(jmsTopic, new IndexUpdateRequest(IndexAction.removeMany, books));
    }

    public Page<Book> searchByRelatedTags(String tag, Pageable pageable) {
        return bookDao.getByRelatedTags(tag, pageable);
    }
}
