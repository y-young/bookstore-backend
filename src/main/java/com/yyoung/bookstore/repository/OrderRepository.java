package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Optional<Order> findByIdAndUserId(Integer orderId, Integer userId);

    List<Order> findByUserId(Integer userId);
}
