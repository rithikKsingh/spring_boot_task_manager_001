package com.example.taskManager.service;

import com.example.taskManager.dto.LoginRequest;
import com.example.taskManager.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest registerRequest);
    String login(LoginRequest loginRequest);
    String registerAdmin(RegisterRequest registerRequest);
}
