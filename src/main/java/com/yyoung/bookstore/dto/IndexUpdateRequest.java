package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.constants.IndexAction;
import com.yyoung.bookstore.entity.Book;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("索引更新请求")
public class IndexUpdateRequest {
    @ApiModelProperty(value = "操作", required = true)
    private IndexAction action;

    @ApiModelProperty(value = "书籍数据", notes = "要添加、更新或删除的书籍")
    private Book book;

    @ApiModelProperty(value = "多本书籍数据", notes = "要批量删除的书籍")
    private List<Book> books;

    public IndexUpdateRequest(IndexAction action) {
        this.action = action;
    }

    public IndexUpdateRequest(IndexAction action, Book book) {
        this.action = action;
        this.book = book;
    }

    public IndexUpdateRequest(IndexAction action, List<Book> books) {
        this.action = action;
        this.books = books;
    }
}
