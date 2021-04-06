package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.repository.UserRepository;
import com.yyoung.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody NewUser newUser) {
        userService.register(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
