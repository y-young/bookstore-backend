package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();

    Order addOrder(Order order);

    Order getOrder(Integer orderId);
    
    Order getOrder(Integer orderId, Integer userId);

    List<Order> getUserOrders(Integer userId);

    List<BookTypeCount> getUserStatistics(Integer userId);
}
