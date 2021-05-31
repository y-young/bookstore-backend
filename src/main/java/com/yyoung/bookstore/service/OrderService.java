package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.entity.OrderItem;
import com.yyoung.bookstore.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(List<OrderItem> items);

    Order viewOrder(Integer orderId);

    List<Order> viewMyOrders();

    List<BookTypeCount> getMyStatistics();
}
