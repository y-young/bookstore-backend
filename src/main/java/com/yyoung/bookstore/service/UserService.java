package com.yyoung.bookstore.service;

import com.yyoung.bookstore.entity.User;

public interface UserService {
    User findByUsername(String username);

    boolean checkPassword(String inputPassword, String userPassword);
    
    void register(String username, String password, String email);
}
