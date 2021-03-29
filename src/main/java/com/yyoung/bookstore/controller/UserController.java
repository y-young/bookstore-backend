package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.service.UserService;
import com.yyoung.bookstore.utils.controller.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestParam(value = "username") String username, @RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        userService.register(username, password, email);
        return new ApiResponse(HttpStatus.OK);
    }
}
