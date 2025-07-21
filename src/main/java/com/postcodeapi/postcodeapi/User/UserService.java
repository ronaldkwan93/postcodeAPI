package com.postcodeapi.postcodeapi.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(String.valueOf(Role.valueOf("USER")));
        return this.userRepository.save(user);
    }

    public User loginUser(LoginDTO user) {
        User existingUser = this.userRepository.findByEmail(user.getEmail()).orElse(null);

        if(existingUser != null) {
            if(!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                throw new RuntimeException("User does not match");
            }
            return existingUser;
        }
            throw new RuntimeException("User not found");
    }
}
