package com.yyoung.bookstore.dto;

import com.yyoung.bookstore.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户消费情况")
public class UserConsumption extends OrderStatistics {
    private User user;

    public UserConsumption(User user, Long orderCount, Long bookCount, Long total) {
        super(orderCount, bookCount, total);
        this.user = user;
    }
}
