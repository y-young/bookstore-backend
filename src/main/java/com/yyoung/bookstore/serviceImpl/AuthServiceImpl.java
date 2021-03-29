package com.yyoung.bookstore.serviceImpl;

import com.yyoung.bookstore.dto.LoginCredentials;
import com.yyoung.bookstore.entity.AuthUser;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.service.AuthService;
import com.yyoung.bookstore.service.UserService;
import com.yyoung.bookstore.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public String login(LoginCredentials loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user == null || !userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
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
        return token;
    }
}
