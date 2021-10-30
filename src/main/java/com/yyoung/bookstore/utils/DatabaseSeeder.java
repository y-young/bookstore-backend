package com.yyoung.bookstore.utils;

import com.yyoung.bookstore.constants.Role;
import com.yyoung.bookstore.entity.Email;
import com.yyoung.bookstore.entity.User;
import com.yyoung.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DatabaseSeeder {
    private final UserRepository userRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (!userRepository.existsById(1)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode("admin");
            User user = new User();
            user.setId(1);
            user.setUsername("admin");
            user.setPassword(password);
            user.setEmail(new Email("i@gpx.moe", user));
            user.setRole(Role.admin);
            user.setDisabled(false);
            userRepository.save(user);
        }
    }
}

