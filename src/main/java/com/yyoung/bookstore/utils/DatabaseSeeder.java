package com.yyoung.bookstore.utils;

import com.yyoung.bookstore.constants.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DatabaseSeeder {
    private final JdbcTemplate jdbcTemplate;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        List<Integer> result = jdbcTemplate.query("SELECT id FROM user WHERE id=1", (rs, i) -> rs.getInt("id"));
        if (result.isEmpty()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode("admin");
            jdbcTemplate.update("INSERT INTO user (id, username, email, password, role, disabled) VALUES (?, ?, ?, ?, ?, ?)", 1, "admin", "i@gpx.moe", password, Role.admin.toString(), false);
        }
    }
}

