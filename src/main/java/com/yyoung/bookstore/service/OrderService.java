package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.OrderDto;
import com.yyoung.bookstore.dto.OrderItem;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(List<OrderItem> items);

    OrderDto viewOrder(Integer orderId);
}
