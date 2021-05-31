package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.dto.BookTypeCount;
import com.yyoung.bookstore.entity.OrderItem;
import com.yyoung.bookstore.dto.api.DataResponse;
import com.yyoung.bookstore.entity.Order;
import com.yyoung.bookstore.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

    @ApiOperation("查看所有订单")
    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public DataResponse<List<Order>> viewAllOrders() {
        return new DataResponse<>(orderService.viewAllOrders());
    }

    @ApiOperation("提交订单")
    @PostMapping
    public DataResponse<Order> placeOrder(@RequestBody @NotEmpty(message = "订单中没有商品") List<@Valid OrderItem> items) {
        return new DataResponse<>(orderService.placeOrder(items));
    }

    @ApiOperation("查看订单详情")
    // Access control implemented in service
    @GetMapping("/{orderId}")
    public DataResponse<Order> viewOrder(@PathVariable Integer orderId) {
        return new DataResponse<>(orderService.viewOrder(orderId));
    }

    @ApiOperation("查看当前用户的所有订单")
    @GetMapping("/my")
    public DataResponse<List<Order>> viewMyOrders() {
        return new DataResponse<>(orderService.viewMyOrders());
    }

    @ApiOperation("查看当前用户购书情况")
    @GetMapping("/my/statistics")
    public DataResponse<List<BookTypeCount>> getMyStatistics() {
        return new DataResponse<>(orderService.getMyStatistics());
    }
}
