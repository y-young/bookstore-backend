package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
