package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.constants.SecurityConstants;
import com.yyoung.bookstore.dto.AuthResult;
import com.yyoung.bookstore.dto.LoginCredentials;
import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.dto.api.DataResponse;
import com.yyoung.bookstore.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseEntity<DataResponse<AuthResult>> login(@RequestBody LoginCredentials loginCredentials) {
        AuthResult authResult = userService.login(loginCredentials);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, authResult.getAuthorization());
        return new ResponseEntity<>(new DataResponse<>(authResult), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody NewUser newUser) {
        userService.register(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}