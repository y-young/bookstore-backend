package com.yyoung.bookstore.utils.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse extends ResponseEntity<ResponseBody> {
    public ApiResponse(HttpStatus status) {
        super(status);
    }
    
    public ApiResponse(ResponseBody body) {
        super(body, HttpStatus.OK);
    }

    public ApiResponse(ResponseBody body, HttpStatus status) {
        super(body, status);
    }

    public ApiResponse(HttpHeaders httpHeaders, HttpStatus status) {
        super(httpHeaders, status);
    }
}
