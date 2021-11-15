package com.yyoung.bookstore.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@Node("Book")
@ApiModel(value = "书籍", description = "仅在Neo4j中使用")
public class BookNode {
    @Id
    private final Integer id;

    @Relationship(type = "HAS_TAG")
    private List<Tag> tags = new ArrayList<>();

    public BookNode(Integer id) {
        this.id = id;
    }

    public void hasTag(Tag tag) {
        this.tags.add(tag);
    }
}
