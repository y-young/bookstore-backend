package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.validation.PasswordsEqualConstraint;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@PasswordsEqualConstraint
@ApiModel(value = "用户注册信息")
public class NewUser {
    @NotBlank(message = "用户名为必填项")
    private String username;

    @NotBlank(message = "电子邮件地址为必填项")
    @Email(message = "请输入合法的电子邮件地址")
    private String email;

    @NotBlank(message = "密码为必填项")
    private String password;

    @NotBlank(message = "确认密码为必填项")
    private String confirmPassword;
}

