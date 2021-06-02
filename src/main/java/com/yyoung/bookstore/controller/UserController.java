package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.constants.SecurityConstants;
import com.yyoung.bookstore.dto.AuthResult;
import com.yyoung.bookstore.dto.LoginCredentials;
import com.yyoung.bookstore.dto.NewUser;
import com.yyoung.bookstore.dto.UserConsumption;
import com.yyoung.bookstore.dto.api.DataResponse;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @ApiOperation("获取所有用户")
    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public DataResponse<List<User>> getUsers() {
        return new DataResponse<>(userService.getAll());
    }

    @ApiOperation("禁用用户")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/{userId}/disable")
    public ResponseEntity<?> disableUser(@PathVariable Integer userId) {
        userService.disableUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("启用用户")
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/{userId}/enable")
    public ResponseEntity<?> enableUser(@PathVariable Integer userId) {
        userService.enableUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("查看用户消费榜")
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/statistics")
    public DataResponse<List<UserConsumption>> getRank(@RequestParam(value = "start", required = false) Date start, @RequestParam(value = "end", required = false) Date end) {
        return new DataResponse<>(userService.getRank(start, end));
    }

    @ApiOperation("查看本人消费情况")
    @GetMapping("/my/statistics")
    public DataResponse<UserConsumption> getMyStatistics(@RequestParam(value = "start", required = false) Date start, @RequestParam(value = "end", required = false) Date end) {
        return new DataResponse<>(userService.getMyStatistics(start, end));
    }
}
