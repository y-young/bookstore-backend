package com.yyoung.bookstore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("订单")
public class OrderDto {
    private Integer id;

    @ApiModelProperty("书籍列表")
    private List<OrderItem> items = new ArrayList<>();

    private Date time;

    public void addItem(OrderItem item) {
        this.items.add(item);
    }
}
