package com.yyoung.bookstore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("书籍购买量分类统计")
public class BookTypeCount {
    @ApiModelProperty("书籍类型")
    private String type;

    @ApiModelProperty("购买数量")
    private Long count;
}
