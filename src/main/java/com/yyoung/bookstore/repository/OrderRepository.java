package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Optional<Order> findByIdAndUserId(Integer orderId, Integer userId);

    List<Order> findByUserId(Integer userId);

    @Query("select new com.yyoung.bookstore.dto.BookTypeCount(b.type, sum(oi.amount)) from Order o join o.items oi join oi.book b where o.user.id = :userId group by b.type order by sum(oi.amount) desc")
    List<BookTypeCount> getUserStatistics(Integer userId);
}
