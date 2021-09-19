package com.yyoung.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@Entity
@Table(name = "order_items")
@IdClass(OrderItemPK.class)
@ApiModel("订单项")
public class OrderItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Id
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    @Positive(message = "购买数量必须为正数")
    @ApiModelProperty("购买数量")
    private Integer amount;
}

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
class OrderItemPK implements Serializable {
    private Book book;

    private Order order;
}