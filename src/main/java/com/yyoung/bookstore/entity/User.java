package com.yyoung.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yyoung.bookstore.constants.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.user;

    @Column(nullable = false)
    private Boolean disabled = false;

    @OneToMany
    private List<Book> cart = new ArrayList<>();

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
