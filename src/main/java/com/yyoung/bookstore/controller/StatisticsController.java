package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.dto.api.DataResponse;
import com.yyoung.bookstore.service.StatisticsService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsController {
    private final StatisticsService statisticsService;

    @ApiOperation("更新并获取访问量计数")
    @GetMapping("/updatePageView")
    public DataResponse<Long> updatePageView() {
        return new DataResponse<>(statisticsService.updatePageView());
    }
}
