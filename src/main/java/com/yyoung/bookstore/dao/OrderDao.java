package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.OrderItem;
import com.yyoung.bookstore.entity.User;

import java.util.List;

public interface OrderDao {
    Integer placeOrder(List<OrderItem> items, float total, User user);
}
