package com.yyoung.bookstore.utils.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse extends ResponseEntity<ResponseBody> {
    public ApiResponse(ResponseBody body) {
        super(body, HttpStatus.OK);
    }

    public ApiResponse(ResponseBody body, HttpStatus status) {
        super(body, status);
    }
}
