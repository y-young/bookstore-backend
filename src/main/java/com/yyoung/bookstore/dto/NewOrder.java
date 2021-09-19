package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.entity.OrderItem;
import com.yyoung.bookstore.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("新订单")
public class NewOrder {
    private User user;

    private List<OrderItem> items;
}
