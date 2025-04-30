package com.example.authentication.service;

import com.example.authentication.config.SecurityConfig.jwtConfig.JwtUtil;
import com.example.authentication.dto.LoginRequest;
import com.example.authentication.dto.RegisterRequest;
import com.example.authentication.dto.RoleName;
import com.example.authentication.entity.Role;
import com.example.authentication.entity.User;
import com.example.authentication.repository.RoleRepository;
import com.example.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;

    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.username())) {
            throw new RuntimeException("Username already exists");
        }
        if(req.password().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        Role roleUser = roleRepo.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = User.builder().username(req.username())
                .password(encoder.encode(req.password()))
                .roles(Set.of(roleUser))
                .build();
        userRepo.save(user);
    }

    public String login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        return jwtUtil.generateJwtToken((UserDetails) auth.getPrincipal());
    }
}
