package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.LoginCredentials;
import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.entity.User;

public interface UserService {
    User findByUsername(String username);

    boolean checkPassword(String inputPassword, String userPassword);

    void register(NewUser newUser);

    String login(LoginCredentials loginRequest);
}
