package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.OrderDto;
import com.yyoung.bookstore.dto.OrderItem;
import com.yyoung.bookstore.entity.User;

import java.util.List;

public interface OrderDao {
    Integer addOrder(List<OrderItem> items, float total, User user);

    OrderDto getOrder(Integer orderId, Integer userId);

    List<OrderDto> getUserOrders(Integer userId);
}
