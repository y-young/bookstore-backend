package com.yyoung.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginCredentials {
    private String username;
    private String password;
}
