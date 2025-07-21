package com.postcodeapi.postcodeapi.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner DataSeed(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@admin")) {
                User admin = new User();
                admin.setEmail("admin@admin");
                admin.setName("Admin");
                admin.setPassword(passwordEncoder.encode("admin")); // Secure password
                admin.setRole(String.valueOf(Role.valueOf("ADMIN")));
                userRepository.save(admin);
            }
        };
    }
}
