package com.yyoung.bookstore.utils.controller;

import lombok.Data;

@Data
public class ResponseBody<T> {
    private String message = "success";

    private T data;

    public ResponseBody() {
    }

    public ResponseBody(String message) {
        this.message = message;
    }

    public ResponseBody(T data) {
        this.data = data;
    }

    public ResponseBody(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
