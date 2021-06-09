package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.validation.PasswordsEqualConstraint;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@PasswordsEqualConstraint
@ApiModel(value = "修改密码请求")
public class PasswordUpdateRequest {
    @NotBlank(message = "当前密码为必填项")
    private String currentPassword;

    @NotBlank(message = "新密码为必填项")
    private String password;

    @NotBlank(message = "确认密码为必填项")
    private String confirmPassword;
}
