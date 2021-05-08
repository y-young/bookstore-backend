package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.dto.api.DataResponse;
import com.yyoung.bookstore.entity.Book;
import com.yyoung.bookstore.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("书籍")
@RequestMapping("/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {
    private final BookService bookService;

    @ApiOperation("获取所有书籍列表")
    @GetMapping
    public DataResponse<List<Book>> listBooks() {
        return new DataResponse<>(bookService.findAll());
    }

    @ApiOperation("获取指定书籍信息")
    @GetMapping("/{bookId}")
    public DataResponse<Book> getBook(@PathVariable Integer bookId) {
        return new DataResponse<>(bookService.findById(bookId));
    }
}
