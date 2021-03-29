package com.yyoung.bookstore.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/test")
    public String test(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Test %s!", name);
    }
}
