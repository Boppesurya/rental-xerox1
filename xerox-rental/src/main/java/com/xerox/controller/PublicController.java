package com.xerox.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xerox.repository.UserRepository;

@RestController
@RequestMapping("/api/public")
public class PublicController {
	private static final Logger logger = LoggerFactory.getLogger(PublicController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PublicController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        logger.info("Received login request: {}", credentials);
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            logger.warn("Invalid request: username or password missing");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Username and password are required"));
        }

        return userRepository.findByUsername(username)
                .map(user -> {
                    logger.debug("User found: {}, password hash: {}", username, user.getPassword());
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        logger.info("Authentication successful for {}", username);
                        return ResponseEntity.ok(Map.of(
                                "username", user.getUsername(),
                                "role", user.getRole().replace("ROLE_", "")
                        ));
                    } else {
                        logger.warn("Password mismatch for {}", username);
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", "Invalid password"));
                    }
                })
                .orElseGet(() -> {
                    logger.warn("User not found: {}", username);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "User not found"));
                });
    }
}
