package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.dto.UserConsumption;
import com.yyoung.bookstore.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAll();

    User findByUsername(String username);

    void save(NewUser newUser);

    boolean existsByUsername(String username);

    void disableById(Integer userId);

    void enableById(Integer userId);

    List<UserConsumption> getRank(Optional<Date> start, Optional<Date> end);
}
