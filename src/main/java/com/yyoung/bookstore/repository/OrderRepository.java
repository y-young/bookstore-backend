package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
