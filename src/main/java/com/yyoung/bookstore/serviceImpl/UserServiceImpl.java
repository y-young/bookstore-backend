package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.UserDao;
import com.yyoung.bookstore.dto.*;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.exception.BusinessLogicException;
import com.yyoung.bookstore.service.UserService;
import com.yyoung.bookstore.utils.Helpers;
import com.yyoung.bookstore.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    public List<User> getAll() {
        return userDao.getAll();
    }

    public boolean checkPassword(String inputPassword, String userPassword) {
        return bCryptPasswordEncoder.matches(inputPassword, userPassword);
    }

    public void register(NewUser newUser) {
        if (userDao.existsByUsername(newUser.getUsername())) {
            throw new BusinessLogicException("用户名已存在");
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        User user = modelMapper.map(newUser, User.class);
        userDao.save(user);
    }

    public AuthResult login(LoginCredentials loginRequest) {
        User user = userDao.findByUsername(loginRequest.getUsername());
        if (user == null || !checkPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        AuthUser authUser = new AuthUser(user);
        if (!authUser.isEnabled()) {
            throw new BadCredentialsException("您的账户已被禁用");
        }
        List<String> authorities = authUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = JwtUtils.createToken(user.getUsername(), user.getId().toString(), authorities);
        return new AuthResult(user, token);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString();
        return userDao.findByUsername(username);
    }

    public void disableUser(Integer userId) {
        User currentUser = getCurrentUser();
        if (userId.equals(currentUser.getId())) {
            throw new BusinessLogicException("不允许禁用当前用户");
        }
        User user = userDao.findById(userId);
        user.setDisabled(true);
        userDao.save(user);
    }

    public void enableUser(Integer userId) {
        User user = userDao.findById(userId);
        user.setDisabled(false);
        userDao.save(user);
    }

    public List<UserConsumption> getRank(Date start, Date end) {
        if (Helpers.hasDateRange(start, end)) {
            return userDao.getRank(start, end);
        }
        return userDao.getRank();
    }

    public UserConsumption getMyStatistics(Date start, Date end) {
        User user = getCurrentUser();
        if (Helpers.hasDateRange(start, end)) {
            return userDao.getUserStatistics(user.getId(), start, end);
        }
        return userDao.getUserStatistics(user.getId());
    }

    public void updatePassword(PasswordUpdateRequest request) {
        User user = getCurrentUser();
        if (!checkPassword(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessLogicException("当前密码不正确");
        }
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userDao.save(user);
    }
}
