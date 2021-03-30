package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.constants.SecurityConstants;
import com.yyoung.bookstore.dto.LoginCredentials;
import com.yyoung.bookstore.service.AuthService;
import com.yyoung.bookstore.utils.controller.ApiResponse;

import com.yyoung.bookstore.utils.controller.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials loginCredentials) {
        String token = authService.login(loginCredentials);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }
}
