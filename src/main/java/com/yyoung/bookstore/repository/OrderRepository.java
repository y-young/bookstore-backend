package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
