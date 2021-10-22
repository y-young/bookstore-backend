package com.yyoung.bookstore.search.dto.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse extends ResponseEntity<ErrorResponseBody> {
    public ErrorResponse(HttpStatus status) {
        super(status);
    }

    public ErrorResponse(String message) {
        super(new ErrorResponseBody(message), HttpStatus.BAD_REQUEST);
    }

    public ErrorResponse(String message, HttpStatus status) {
        super(new ErrorResponseBody(message), status);
    }
}
