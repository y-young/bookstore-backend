package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.entity.Order;

import java.util.List;

public interface OrderDao {
    Order addOrder(Order order);

    Order getOrder(Integer orderId, Integer userId);

    List<Order> getUserOrders(Integer userId);
}
