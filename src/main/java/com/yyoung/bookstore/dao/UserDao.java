package com.yyoung.bookstore.dao;

import com.yyoung.bookstore.entity.User;

public interface UserDao {
    User findByUsername(String username);

    void save(String username, String password, String email);
}
