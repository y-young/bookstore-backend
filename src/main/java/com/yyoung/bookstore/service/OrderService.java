package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.dto.OrderStatistics;
import com.yyoung.bookstore.entity.OrderItem;
import com.yyoung.bookstore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Page<Order> viewAllOrders(String bookTitle, Date start, Date end, Pageable pageable);

    void placeOrder(List<OrderItem> items);

    Order viewOrder(Integer orderId);

    Page<Order> viewMyOrders(String bookTitle, Date start, Date end, Pageable pageable);

    List<BookTypeCount> getMyBookStatistics(Date start, Date end);

    OrderStatistics getStatistics(Date start, Date end);
}
