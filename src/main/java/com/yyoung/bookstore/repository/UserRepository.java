package com.yyoung.bookstore.repository;

import com.yyoung.bookstore.constants.Role;
import com.yyoung.bookstore.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private Role mapStringToRole(String value) {
        if (value.equals(Role.admin.toString())) {
            return Role.admin;
        }
        return Role.user;
    }

    public User findByUsername(String username) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM user WHERE username=?",
                (resultSet, i) -> new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        mapStringToRole(resultSet.getString("role")),
                        resultSet.getBoolean("disabled")
                ),
                username
        );
        return users.isEmpty() ? null : users.get(0);
    }

    public void save(User user) {
        jdbcTemplate.update(
                "INSERT INTO user (username, email, password, disabled, role) VALUES (?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getDisabled(),
                user.getRole().toString()
        );
    }
}
