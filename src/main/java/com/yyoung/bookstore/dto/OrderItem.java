package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.entity.Book;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@ApiModel("订单项")
public class OrderItem extends Book {
    @Positive(message = "购买数量必须为正数")
    @ApiModelProperty("购买数量")
    private Integer amount;
}
