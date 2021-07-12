package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.dto.OrderStatistics;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    Page<Order> getAllOrders(Pageable pageable);

    Page<Order> getAllOrders(String bookTitle, Pageable pageable);

    Page<Order> getAllOrders(Date start, Date end, Pageable pageable);

    Page<Order> getAllOrders(String bookTitle, Date start, Date end, Pageable pageable);

    Order addOrder(Order order);

    Order getOrder(Integer orderId);

    Order getUserOrder(Integer orderId, User user);

    Page<Order> getUserOrders(User user, Pageable pageable);

    Page<Order> getUserOrders(User user, String bookTitle, Pageable pageable);

    Page<Order> getUserOrders(User user, Date start, Date end, Pageable pageable);

    Page<Order> getUserOrders(User user, String bookTitle, Date start, Date end, Pageable pageable);

    List<BookTypeCount> getUserBookStatistics(Integer userId);

    List<BookTypeCount> getUserBookStatistics(Integer userId, Date start, Date end);

    OrderStatistics getStatistics();

    OrderStatistics getStatistics(Date start, Date end);
}
