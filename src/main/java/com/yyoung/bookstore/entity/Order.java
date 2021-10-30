package com.yyoung.bookstore.entity;

import com.yyoung.bookstore.constants.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders", indexes = {
        @Index(columnList = "user_id")
})
@ApiModel("订单")
public class Order {
    @Id
    @Column(columnDefinition = "INT UNSIGNED")
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "MEDIUMINT UNSIGNED")
    @ApiModelProperty("总金额/（分）")
    private Integer total = 0;

    @Column(nullable = false, columnDefinition = "SMALLINT(4) UNSIGNED")
    @ApiModelProperty("商品总数")
    private Integer totalAmount = 0;

    @Column(nullable = false, columnDefinition = "TINYINT(1) UNSIGNED")
    @Enumerated
    @ApiModelProperty("订单状态")
    private OrderStatus status;

    @ApiModelProperty("失败原因")
    private String failedReason = "";

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date time;

    public void addItem(OrderItem item) {
        item.setOrder(this);
        this.items.add(item);
    }
}
