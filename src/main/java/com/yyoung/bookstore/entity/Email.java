package com.yyoung.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "emails")
@NoArgsConstructor
@ApiModel("电子邮件地址")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Email {
    @Id
    @Column(columnDefinition = "INT(10) UNSIGNED")
    private Integer id;

    @Column(nullable = false, columnDefinition = "VARCHAR(320)")
    private String address;

    @MapsId
    @JsonIgnore
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", columnDefinition = "INT(10) UNSIGNED")
    private User user;

    public Email(String address, User user) {
        this.address = address;
        this.user = user;
    }
}
