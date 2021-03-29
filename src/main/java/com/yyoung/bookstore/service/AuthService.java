package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.LoginCredentials;

public interface AuthService {
    String login(LoginCredentials loginRequest);
}
