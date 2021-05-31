package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.dto.UserConsumption;
import com.yyoung.bookstore.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Override
    List<User> findAll();

    User findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select new com.yyoung.bookstore.dto.UserConsumption(u, count(o), sum(o.totalAmount), sum(o.total)) from Order o join o.user u group by u.id order by sum(o.total) desc")
    List<UserConsumption> getRank();
}
