package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.NewOrder;
import com.yyoung.bookstore.entity.Order;

public interface OrderProcessService {
    void processOrder(NewOrder newOrder);

    Order updateStocks(Order order);
}
