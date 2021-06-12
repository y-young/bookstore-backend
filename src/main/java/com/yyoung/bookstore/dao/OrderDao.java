package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.dto.OrderStatistics;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.entity.User;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();

    List<Order> getAllOrders(String bookTitle);

    List<Order> getAllOrders(Date start, Date end);

    List<Order> getAllOrders(String bookTitle, Date start, Date end);

    Order addOrder(Order order);

    Order getOrder(Integer orderId);

    Order getUserOrder(Integer orderId, User user);

    List<Order> getUserOrders(User user);

    List<Order> getUserOrders(User user, String bookTitle);

    List<Order> getUserOrders(User user, Date start, Date end);

    List<Order> getUserOrders(User user, String bookTitle, Date start, Date end);

    List<BookTypeCount> getUserBookStatistics(Integer userId);

    List<BookTypeCount> getUserBookStatistics(Integer userId, Date start, Date end);

    OrderStatistics getStatistics();

    OrderStatistics getStatistics(Date start, Date end);
}
