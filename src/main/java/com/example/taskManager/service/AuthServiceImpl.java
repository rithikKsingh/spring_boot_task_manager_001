package com.example.taskManager.service;

import com.example.taskManager.dto.LoginRequest;
import com.example.taskManager.dto.RegisterRequest;
import com.example.taskManager.entity.Role;
import com.example.taskManager.entity.User;
import com.example.taskManager.repository.UserRepository;
import com.example.taskManager.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public String register(RegisterRequest registerRequest){
        User user= User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String registerAdmin(RegisterRequest registerRequest){
        User user= User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        return "Admin registered successfully";
    }

    @Override
    public String login(LoginRequest loginRequest){
        User user=userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
                ()->new RuntimeException("Invalid Username")
        );

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }

//        return "Login successful";
        return jwtUtil.generateToken(user.getUsername());
    }
}
