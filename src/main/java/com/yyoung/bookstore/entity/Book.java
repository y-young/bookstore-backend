package com.yyoung.bookstore.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Table(name = "books")
@ApiModel("书籍")
public class Book {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    @ApiModelProperty("书名")
    private String title;

    @ApiModelProperty("作者")
    private String author;

    @NaturalId
    @ApiModelProperty("ISBN")
    private String isbn;

    @Column(nullable = false)
    @ApiModelProperty("库存")
    private Integer stock = 0;

    @Column(nullable = false)
    @ApiModelProperty("价格")
    private Double price = 0.0;

    @ApiModelProperty("封面图片路径")
    private String cover;
}
