package com.yyoung.bookstore.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name="books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String author;

    @NaturalId
    private String isbn;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(nullable = false)
    private Double price = 0.0;

    private String cover;
}
