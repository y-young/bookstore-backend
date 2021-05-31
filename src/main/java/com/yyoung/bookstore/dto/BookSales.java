package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.entity.Book;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("书籍销量统计")
public class BookSales {
    private Book book;

    @ApiModelProperty("销量")
    private Long sales;
}
