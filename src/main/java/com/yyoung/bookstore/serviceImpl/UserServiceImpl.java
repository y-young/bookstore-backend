package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.UserDao;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public boolean checkPassword(String inputPassword, String userPassword) {
        return bCryptPasswordEncoder.matches(inputPassword, userPassword);
    }

    public void register(String username, String password, String email) {
        userDao.save(username, bCryptPasswordEncoder.encode(password), email);
    }
}
