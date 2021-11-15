package com.yyoung.bookstore.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Data
@Node
@ApiModel("书籍标签")
public class Tag {
    @Id
    private final String name;

    @ApiModelProperty(value = "相关标签", notes = "从低级别指向高级别，如：英国文学->文学")
    @Relationship(type = "IS_RELATED_TO")
    private List<Tag> relatedTags = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
}
