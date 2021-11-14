package com.yyoung.bookstore.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("bookCovers")
@ApiModel("书籍封面")
public class BookCover {
    @Id
    private String id;

    private byte[] data;
}
