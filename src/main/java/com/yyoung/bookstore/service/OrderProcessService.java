package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.NewOrder;

public interface OrderProcessService {
    void processOrder(NewOrder newOrder);
}
