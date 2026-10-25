package com.taskmanager.backened.controller;

import com.taskmanager.backened.model.User;
import com.taskmanager.backened.repository.UserRepository;
import com.taskmanager.backened.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Please enter a valid email address.");
        }

        boolean emailExists = userRepository.findAll().stream()
                .anyMatch(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(user.getEmail().trim()));

        if (emailExists) {
            return ResponseEntity.badRequest().body("Email already exists! Please Log In.");
        }

        user.setEmail(user.getEmail().trim());
        // Default to SEEKER if no role is chosen
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("SEEKER");
        }
        
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("username");
        if (email == null || email.trim().isEmpty()) {
            email = credentials.get("email");
        }
        String password = credentials.get("password");

        if (email == null || password == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        final String loginEmail = email.trim();
        Optional<User> userOpt = userRepository.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(loginEmail))
                .findFirst();

        if (userOpt.isEmpty() || !password.equals(userOpt.get().getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(loginEmail);
        String role = userOpt.get().getRole() != null ? userOpt.get().getRole() : "SEEKER";

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", role); // Send role to React
        
        return ResponseEntity.ok(response);
    }
}