package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.BookNode;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookNodeRepository extends CrudRepository<BookNode, Integer> {
    @Query("match (t:Tag)-[:IS_RELATED_TO*0..2]-()-[:HAS_TAG]-(b:Book) where t.name=$tag return distinct b")
    List<BookNode> findBookNodeByRelatedTags(String tag);
}
