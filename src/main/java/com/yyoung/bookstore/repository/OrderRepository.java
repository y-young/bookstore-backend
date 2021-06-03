package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.dto.OrderStatistics;
import com.yyoung.bookstore.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Override
    List<Order> findAll();

    List<Order> findByItemsBookTitleContains(String bookTitle);

    List<Order> findByTimeBetween(Date start, Date end);

    List<Order> findByItemsBookTitleContainsAndTimeBetween(String bookTitle, Date start, Date end);

    Optional<Order> findByIdAndUserId(Integer orderId, Integer userId);

    List<Order> findByUserId(Integer userId);

    List<Order> findByUserIdAndItemsBookTitleContains(Integer userId, String bookTitle);

    List<Order> findByUserIdAndTimeBetween(Integer userId, Date start, Date end);

    List<Order> findByUserIdAndItemsBookTitleContainsAndTimeBetween(Integer userId, String bookTitle, Date start, Date end);

    @Query("select new com.yyoung.bookstore.dto.BookTypeCount(b.type, sum(oi.amount)) from Order o join o.items oi join oi.book b where o.user.id = :userId group by b.type order by sum(oi.amount) desc")
    List<BookTypeCount> getUserBookStatistics(Integer userId);

    @Query("select new com.yyoung.bookstore.dto.BookTypeCount(b.type, sum(oi.amount)) from Order o join o.items oi join oi.book b where o.user.id = :userId and o.time between :start and :end group by b.type order by sum(oi.amount) desc")
    List<BookTypeCount> getUserBookStatistics(Integer userId, Date start, Date end);

    @Query("select new com.yyoung.bookstore.dto.OrderStatistics(count(o), sum(o.totalAmount), sum(o.total)) from Order o")
    OrderStatistics getStatistics();

    @Query("select new com.yyoung.bookstore.dto.OrderStatistics(count(o), sum(o.totalAmount), sum(o.total)) from Order o where o.time between :start and :end")
    OrderStatistics getStatistics(Date start, Date end);
}
