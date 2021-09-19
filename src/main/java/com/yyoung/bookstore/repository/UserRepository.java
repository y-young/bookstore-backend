package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.dto.UserConsumption;
import com.yyoung.bookstore.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Override
    List<User> findAll();

    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select new com.yyoung.bookstore.dto.UserConsumption(u, count(o), sum(o.totalAmount), sum(o.total)) from Order o join o.user u where o.status = 'completed' group by u.id order by sum(o.total) desc")
    List<UserConsumption> getRank();

    @Query("select new com.yyoung.bookstore.dto.UserConsumption(u, count(o), sum(o.totalAmount), sum(o.total)) from Order o join o.user u where o.time between :start and :end and o.status = 'completed' group by u.id order by sum(o.total) desc")
    List<UserConsumption> getRank(Date start, Date end);

    @Query("select new com.yyoung.bookstore.dto.UserConsumption(u, count(o), sum(o.totalAmount), sum(o.total)) from Order o join o.user u where u.id = :userId and o.status = 'completed'")
    UserConsumption getUserStatistics(Integer userId);

    @Query("select new com.yyoung.bookstore.dto.UserConsumption(u, count(o), sum(o.totalAmount), sum(o.total)) from Order o join o.user u where u.id = :userId and o.time between :start and :end and o.status = 'completed'")
    UserConsumption getUserStatistics(Integer userId, Date start, Date end);
}
