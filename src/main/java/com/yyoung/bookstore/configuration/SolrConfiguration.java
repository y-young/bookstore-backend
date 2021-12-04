package com.yyoung.bookstore.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("solr")
@Getter
@Setter
public class SolrConfiguration {
    /*
     * If SolrCloud mode is enabled.
     */
    private Boolean cloud;
    /*
     * URL to root Solr path.
     */
    private String url;

    /*
     * Name of Solr collection.
     */
    private String collection;
}
