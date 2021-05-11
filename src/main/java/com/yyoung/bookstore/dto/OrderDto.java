package com.yyoung.bookstore.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("订单")
public class OrderDto {
    private Integer id;

    @ApiModelProperty("书籍列表")
    private List<OrderItem> items = new ArrayList<>();

    private Float total;

    private Date time;

    public OrderDto(Integer id, Float total, Date time) {
        this.id = id;
        this.total = total;
        this.time = time;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }
}
