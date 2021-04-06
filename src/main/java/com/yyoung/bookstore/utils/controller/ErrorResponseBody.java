package com.yyoung.bookstore.utils.controller;

import lombok.Data;

@Data
public class ErrorResponseBody {
    private String message = "";

    public ErrorResponseBody(String message) {
        this.message = message;
    }
}
