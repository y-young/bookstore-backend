package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("身份验证结果")
public class AuthResult {
    @ApiModelProperty("用户信息")
    private User user;

    @ApiModelProperty("Header Authorization信息")
    private String authorization;
}
