package com.travelagent.app.controllers;

import com.travelagent.app.models.User;
import com.travelagent.app.repositories.UserRepository;
import com.travelagent.app.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        System.out.println(!passwordEncoder.matches(user.getPassword(), dbUser.getPassword()));
        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) { // NEED TO ENCODE PASSWORDS IN DB FOR THIS TO WORK - TOKEN: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJua29sb3ZvcyIsImlhdCI6MTczNzc1Mzg5MCwiZXhwIjoxNzM3ODQwMjkwfQ.W4xKJWv3vHqVYWjdo8RSoA2Rf0ShXeaBqFsdXoKxKFY
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(dbUser.getUsername());
        return Map.of("token", token);
    }
}
