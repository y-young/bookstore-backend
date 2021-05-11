package com.yyoung.bookstore.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@ApiModel("订单")
public class Order {
    @Id
    private Integer id;

    private Integer userId;

    @ApiModelProperty("总金额")
    private Float total;
    
    private Date time;
}
