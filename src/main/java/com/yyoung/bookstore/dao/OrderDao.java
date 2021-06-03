package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.dto.OrderStatistics;
import com.yyoung.bookstore.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();

    List<Order> getAllOrders(Date start, Date end);

    Order addOrder(Order order);

    Order getOrder(Integer orderId);

    Order getUserOrder(Integer orderId, Integer userId);

    List<Order> getUserOrders(Integer userId);

    List<Order> getUserOrders(Integer userId, Date start, Date end);

    List<BookTypeCount> getUserBookStatistics(Integer userId);

    List<BookTypeCount> getUserBookStatistics(Integer userId, Date start, Date end);

    OrderStatistics getStatistics();

    OrderStatistics getStatistics(Date start, Date end);
}
