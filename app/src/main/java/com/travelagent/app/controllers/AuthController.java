package com.travelagent.app.controllers;

import com.travelagent.app.models.User;
import com.travelagent.app.models.Role;
import com.travelagent.app.services.RoleService;
import com.travelagent.app.repositories.UserRepository;
import com.travelagent.app.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, RoleService roleService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }    

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        // Set user fields for creation
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        
        // Set role field for user creation
        Role role = roleService.findByName("AGENT");
        user.setRole(role);
        userRepository.save(user);
        
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(password, dbUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        System.out.println(dbUser.getRole().getName());
        String token = jwtUtil.generateToken(dbUser.getUsername(), dbUser.getRole());
        String roleName = dbUser.getRole().getName();
        return Map.of(
            "token", token,
            "role", roleName,
            "username", dbUser.getUsername()
            );
    }
}
