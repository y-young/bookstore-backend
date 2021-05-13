package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dao.UserDao;
import com.yyoung.bookstore.dto.AuthResult;
import com.yyoung.bookstore.dto.LoginCredentials;
import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.entity.AuthUser;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.exception.BusinessLogicException;
import com.yyoung.bookstore.service.UserService;
import com.yyoung.bookstore.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public boolean checkPassword(String inputPassword, String userPassword) {
        return bCryptPasswordEncoder.matches(inputPassword, userPassword);
    }

    public void register(NewUser newUser) {
        if (userDao.existsByUsername(newUser.getUsername())) {
            throw new BusinessLogicException("用户名已存在");
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userDao.save(newUser);
    }

    public AuthResult login(LoginCredentials loginRequest) {
        User user = findByUsername(loginRequest.getUsername());
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
}
