package com.yyoung.bookstore.service;

import com.yyoung.bookstore.dto.*;
import com.yyoung.bookstore.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    List<User> getAll();

    boolean checkPassword(String inputPassword, String userPassword);

    void register(NewUser newUser);

    AuthResult login(LoginCredentials loginRequest);

    User getCurrentUser();

    void disableUser(Integer userId);

    void enableUser(Integer userId);

    List<UserConsumption> getRank(Date start, Date end);

    UserConsumption getMyStatistics(Date start, Date end);

    void updatePassword(PasswordUpdateRequest request);

    boolean checkUsername(String username);
}
