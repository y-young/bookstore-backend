package com.yyoung.bookstore.search.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("书籍")
public class Book implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    @ApiModelProperty("书名")
    private String title;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("ISBN")
    private String isbn;

    @Column(nullable = false)
    @ApiModelProperty("库存量")
    private Integer stock = 0;

    @Column(nullable = false)
    @ApiModelProperty("价格（分）")
    private Integer price;

    @ApiModelProperty("书籍类型")
    private String type;

    @Column(columnDefinition = "TEXT")
    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("封面图片路径")
    private String cover;

    @Column(nullable = false)
    @ApiModelProperty("是否下架")
    private Boolean deleted = false;
}
