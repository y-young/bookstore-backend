package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.constants.AuthConstants;
import com.yyoung.bookstore.dto.LoginCredentials;
import com.yyoung.bookstore.service.AuthService;
import com.yyoung.bookstore.utils.controller.ApiResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginCredentials loginCredentials) {
        String token = authService.login(loginCredentials);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AuthConstants.TOKEN_HEADER, token);
        return new ApiResponse(httpHeaders, HttpStatus.OK);
    }
}
