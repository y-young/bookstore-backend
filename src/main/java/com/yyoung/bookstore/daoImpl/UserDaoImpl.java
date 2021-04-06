package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.UserDao;
import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(NewUser newUser) {
        User user = modelMapper.map(newUser, User.class);
        userRepository.save(user);
    }
}
