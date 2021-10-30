package com.yyoung.bookstore.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;

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
    @Field
    @Column(columnDefinition = "MEDIUMINT UNSIGNED")
    @GeneratedValue
    private Integer id;

    @Field
    @Column(nullable = false)
    @ApiModelProperty("书名")
    private String title;

    @Field
    @ApiModelProperty("作者")
    private String author;

    @Column(columnDefinition = "CHAR(13)")
    @ApiModelProperty("ISBN")
    private String isbn;

    @Column(nullable = false, columnDefinition = "MEDIUMINT(6) UNSIGNED")
    @ApiModelProperty("库存量")
    private Integer stock = 0;

    @Column(nullable = false, columnDefinition = "MEDIUMINT(6) UNSIGNED")
    @ApiModelProperty("价格（分）")
    private Integer price;

    @ApiModelProperty("书籍类型")
    private String type;

    @Field
    @Column(columnDefinition = "TEXT")
    @ApiModelProperty("简介")
    private String description;

    @Column(columnDefinition = "CHAR(36)")
    @ApiModelProperty("封面图片路径")
    private String cover;

    @Column(nullable = false)
    @ApiModelProperty("是否下架")
    private Boolean deleted = false;
}
