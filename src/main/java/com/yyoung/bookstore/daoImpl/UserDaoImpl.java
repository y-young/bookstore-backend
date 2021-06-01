package com.yyoung.bookstore.daoImpl;

import com.yyoung.bookstore.dao.UserDao;
import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.dto.UserConsumption;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.exception.ResourceNotFoundException;
import com.yyoung.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(NewUser newUser) {
        User user = modelMapper.map(newUser, User.class);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void disableById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        user.setDisabled(true);
        userRepository.save(user);
    }

    public void enableById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        user.setDisabled(false);
        userRepository.save(user);
    }

    public List<UserConsumption> getRank(Optional<Date> start, Optional<Date> end) {
        if (start.isPresent() && end.isPresent()) {
            return userRepository.getRankBetween(start.get(), end.get());
        }
        return userRepository.getRank();
    }
}
