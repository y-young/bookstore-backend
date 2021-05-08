package com.yyoung.bookstore.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@ApiModel("书籍")
public class Book {
    @Id
    private Integer id;

    @ApiModelProperty("书名")
    private String title;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("ISBN")
    private String isbn;

    @ApiModelProperty("库存")
    private Integer stock = 0;

    @ApiModelProperty("价格")
    private Float price = 0.0F;

    @ApiModelProperty("书籍类型")
    private String type;

    @ApiModelProperty("封面图片路径")
    private String cover;
}
