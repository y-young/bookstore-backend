package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.UserDao;
import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(NewUser newUser) {
        User user = modelMapper.map(newUser, User.class);
        userRepository.save(user);
    }
}
