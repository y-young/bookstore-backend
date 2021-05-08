package com.yyoung.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yyoung.bookstore.constants.Role;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户")
public class User {
    @Id
    private Integer id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Role role = Role.user;

    private Boolean disabled = false;
}
