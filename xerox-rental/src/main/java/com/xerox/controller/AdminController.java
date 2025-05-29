package com.xerox.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xerox.entity.User;
import com.xerox.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    /**
	     * Adds a new owner user.
	     */
	    @PostMapping("/owners")
	    public ResponseEntity<?> addOwner(@RequestBody UserRequest request, Principal principal) {
	        User admin = userRepository.findByUsername(principal.getName()).orElse(null);
	        if (admin == null) {
	            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
	        }
	        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
	            return ResponseEntity.badRequest().body(Map.of("error", "Username exists"));
	        }
	        User owner = new User();
	        owner.setUsername(request.getUsername());
	        owner.setPassword(passwordEncoder.encode(request.getPassword()));
	        owner.setEmail(request.getEmail());
	        owner.setRole("ROLE_OWNER");
	        owner.setCreatedBy(admin);
	        userRepository.save(owner);
	        return ResponseEntity.ok(Map.of("message", "Owner added"));
	    }

	    /**
	     * Lists all users.
	     */
	    @GetMapping("/users")
	    public ResponseEntity<List<User>> getAllUsers() {
	        return ResponseEntity.ok(userRepository.findAll());
	    }

	    public static class UserRequest {
	        private String username;
	        private String password;
	        private String email;

	        public String getUsername() { return username; }
	        public void setUsername(String username) { this.username = username; }
	        public String getPassword() { return password; }
	        public void setPassword(String password) { this.password = password; }
	        public String getEmail() { return email; }
	        public void setEmail(String email) { this.email = email; }
	    }
}
