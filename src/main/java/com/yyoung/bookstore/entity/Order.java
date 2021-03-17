package com.yyoung.bookstore.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private User user;

    @OneToMany
    private List<Book> books = new ArrayList<>();

    @CreationTimestamp
    private Date time;
}
