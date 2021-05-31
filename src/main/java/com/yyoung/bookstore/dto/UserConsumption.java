package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("用户消费情况")
public class UserConsumption {
    private User user;

    @ApiModelProperty("订单总数")
    private Long orderCount;

    @ApiModelProperty("购书总数")
    private Long bookCount;

    @ApiModelProperty("总消费额")
    private Long total;
}
