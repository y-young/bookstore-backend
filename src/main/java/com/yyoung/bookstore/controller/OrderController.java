package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.dto.OrderDto;
import com.yyoung.bookstore.dto.OrderItem;
import com.yyoung.bookstore.dto.api.DataResponse;
import com.yyoung.bookstore.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
    private final OrderService orderService;

    @ApiOperation("提交订单")
    @PostMapping
    public DataResponse<OrderDto> placeOrder(@RequestBody @NotEmpty(message = "订单中没有商品") List<@Valid OrderItem> items) {
        return new DataResponse<>(orderService.placeOrder(items));
    }

    @ApiOperation("查看订单详情")
    @GetMapping("/{orderId}")
    public DataResponse<OrderDto> viewOrder(@PathVariable Integer orderId) {
        return new DataResponse<>(orderService.viewOrder(orderId));
    }

    @ApiOperation("查看当前用户的所有订单")
    @GetMapping("/my")
    public DataResponse<List<OrderDto>> viewMyOrders() {
        return new DataResponse<>(orderService.viewMyOrders());
    }
}
