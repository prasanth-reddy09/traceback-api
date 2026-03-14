package com.traceback.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.traceback.api.dto.AuthResponse;
import com.traceback.api.dto.LoginRequest;
import com.traceback.api.entity.User;
import com.traceback.api.repository.UserRepository;
import com.traceback.api.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // <-- Inject the Keycard Machine!

    // 1. Register Method (Leave this exactly as you had it!)
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("A user with this email already exists!");
        }
        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);
        
        System.out.println(encodedPassword + " hello");
        return userRepository.save(user);
    }

    // 2. NEW: Login Method
    public AuthResponse loginUser(LoginRequest request) {
        // Find the user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password!"));

        // 🔒 SECURITY: Use Bcrypt to check if the raw password matches the database hash
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password!");
        }

        // Generate the shiny new token!
        String token = jwtUtil.generateToken(user.getEmail());

        // Return the token and user details to the frontend
        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .build();
    }
}