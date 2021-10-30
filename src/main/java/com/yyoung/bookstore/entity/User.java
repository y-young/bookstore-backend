package com.yyoung.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yyoung.bookstore.constants.Role;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "username")
})
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @Column(columnDefinition = "INT(10) UNSIGNED")
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "CHAR(60)")
    private String password;

    @Column(nullable = false, columnDefinition = "TINYINT(1) UNSIGNED")
    @Enumerated
    private Role role = Role.user;

    @Column(nullable = false)
    private Boolean disabled = false;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "user")
    private Email email;
}
