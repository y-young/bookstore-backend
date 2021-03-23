package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.utils.controller.ApiResponse;
import com.yyoung.bookstore.utils.controller.ResponseBody;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/register")
    public ApiResponse register(@RequestParam(value = "username") String username, @RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return new ApiResponse(new ResponseBody(user));
    }
}
