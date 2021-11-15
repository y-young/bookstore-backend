package com.yyoung.bookstore.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookDto {
    @ApiModelProperty("书名")
    @NotBlank(message = "书名为必填项")
    private String title;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("ISBN")
    private String isbn;

    @ApiModelProperty("库存量")
    @NotNull(message = "库存量为必填项")
    @PositiveOrZero(message = "库存量应为非负数")
    private Integer stock = 0;

    @ApiModelProperty("价格（分）")
    @NotNull(message = "价格为必填项")
    @Positive(message = "价格应为正数")
    private Integer price;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("标签")
    private List<String> tags = new ArrayList<>();

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("封面图片路径")
    @NotNull(message = "封面为必选项")
    private String cover;
}
