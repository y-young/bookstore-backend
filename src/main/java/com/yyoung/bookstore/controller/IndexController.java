package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@Api("索引")
@RequestMapping("/index")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {
    private final SearchService searchService;

    @PostMapping("/init")
    @Secured({"ROLE_ADMIN"})
    @ApiOperation("初始化索引")
    public ResponseEntity<?> index() {
        searchService.initIndex();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh")
    @Secured({"ROLE_ADMIN"})
    @ApiOperation("重建索引")
    public ResponseEntity<?> reIndex() {
        searchService.reIndex();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
