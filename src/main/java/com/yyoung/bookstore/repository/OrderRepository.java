package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.dto.OrderStatistics;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> {
    @Override
    Page<Order> findAll(Pageable pageable);

    Page<Order> findByItemsBookTitleContains(String bookTitle, Pageable pageable);

    Page<Order> findByTimeBetween(Date start, Date end, Pageable pageable);

    Page<Order> findByItemsBookTitleContainsAndTimeBetween(String bookTitle, Date start, Date end, Pageable pageable);

    Optional<Order> findByIdAndUser(Integer orderId, User user);

    Page<Order> findByUser(User user, Pageable pageable);

    Page<Order> findDistinctByUserAndItemsBookTitleContains(User user, String bookTitle, Pageable pageable);

    Page<Order> findByUserAndTimeBetween(User user, Date start, Date end, Pageable pageable);

    Page<Order> findDistinctByUserAndItemsBookTitleContainsAndTimeBetween(User user, String bookTitle, Date start, Date end, Pageable pageable);

    @Query("select new com.yyoung.bookstore.dto.BookTypeCount(b.type, sum(oi.amount)) from Order o join o.items oi join oi.book b where o.user.id = :userId and o.status = 'completed' group by b.type order by sum(oi.amount) desc")
    List<BookTypeCount> getUserBookStatistics(Integer userId);

    @Query("select new com.yyoung.bookstore.dto.BookTypeCount(b.type, sum(oi.amount)) from Order o join o.items oi join oi.book b where o.user.id = :userId and o.time between :start and :end and o.status = 'completed' group by b.type order by sum(oi.amount) desc")
    List<BookTypeCount> getUserBookStatistics(Integer userId, Date start, Date end);

    @Query("select new com.yyoung.bookstore.dto.OrderStatistics(count(o), sum(o.totalAmount), sum(o.total)) from Order o where o.status = 'completed'")
    OrderStatistics getStatistics();

    @Query("select new com.yyoung.bookstore.dto.OrderStatistics(count(o), sum(o.totalAmount), sum(o.total)) from Order o where o.time between :start and :end and o.status = 'completed'")
    OrderStatistics getStatistics(Date start, Date end);
}
