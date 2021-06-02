package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.entity.OrderItem;
import com.yyoung.bookstore.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> viewAllOrders(Date start, Date end);

    Order placeOrder(List<OrderItem> items);

    Order viewOrder(Integer orderId);

    List<Order> viewMyOrders(Date start, Date end);

    List<BookTypeCount> getMyBookStatistics(Date start, Date end);
}
