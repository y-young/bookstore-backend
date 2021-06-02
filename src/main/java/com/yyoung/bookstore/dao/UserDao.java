package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.dto.UserConsumption;
import com.yyoung.bookstore.entity.User;

import java.util.Date;
import java.util.List;

public interface UserDao {
    List<User> getAll();

    User findByUsername(String username);

    void save(NewUser newUser);

    boolean existsByUsername(String username);

    void disableById(Integer userId);

    void enableById(Integer userId);

    List<UserConsumption> getRank();

    List<UserConsumption> getRank(Date start, Date end);

    UserConsumption getUserStatistics(Integer userId);

    UserConsumption getUserStatistics(Integer userId, Date start, Date end);
}
