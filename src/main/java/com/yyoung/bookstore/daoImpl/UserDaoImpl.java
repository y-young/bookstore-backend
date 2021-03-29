package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.UserDao;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;

    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(String username, String password, String email) {
        User user = new User(username, password, email);
        userRepository.save(user);
    }
}
