package com.yyoung.bookstore.dto.api;

import lombok.Data;

@Data
public class ErrorResponseBody {
    private String message = "";

    public ErrorResponseBody(String message) {
        this.message = message;
    }
}
