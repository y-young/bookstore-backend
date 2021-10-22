package com.yyoung.bookstore.search.controller;

import com.yyoung.bookstore.search.dto.api.DataResponse;
import com.yyoung.bookstore.search.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("搜索")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {
    private final BookService bookService;

    @ApiOperation("根据书名获取作者")
    @GetMapping("/author")
    public DataResponse<String> getAuthor(@RequestParam(value = "title") String title) {
        return new DataResponse<>(bookService.findAuthorByTitle(title));
    }
}
