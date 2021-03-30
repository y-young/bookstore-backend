package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.service.BookService;
import com.yyoung.bookstore.utils.controller.ApiResponse;
import com.yyoung.bookstore.utils.controller.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("书籍")
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/test")
    public String test(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Test %s!", name);
    }

    @ApiOperation("获取指定书籍信息")
    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable Integer bookId) {
        Book book = bookService.findById(bookId);
//        return new ApiResponse(new ResponseBody<Book>(book));
        return book;
    }
}
