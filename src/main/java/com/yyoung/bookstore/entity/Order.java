package com.yyoung.bookstore.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Order {
    @Id
    private Integer id;

    private Integer userId;

    private Date time;
}
