package com.yyoung.bookstore.exception;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

public class BusinessLogicException extends ResponseStatusException {
    public BusinessLogicException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
