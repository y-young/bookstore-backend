package com.yyoung.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginCredentials {
    @NotBlank(message = "用户名为必填项")
    private String username;

    @NotBlank(message = "密码为必填项")
    private String password;
}
