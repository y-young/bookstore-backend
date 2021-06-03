package com.yyoung.bookstore.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatistics {
    @ApiModelProperty("订单总数")
    private Long orderCount;

    @ApiModelProperty("购书总数")
    private Long bookCount;

    @ApiModelProperty("总消费额")
    private Long total;
}
