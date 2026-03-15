package com.priority.gpstracker.service;

import com.priority.gpstracker.dto.LoginRequest;
import com.priority.gpstracker.dto.LoginResponse;
import com.priority.gpstracker.dto.SignupRequest;
import com.priority.gpstracker.dto.UserResponse;

import java.util.UUID;

public interface UserService {

   UserResponse signup(SignupRequest request);

   LoginResponse login(LoginRequest request);

   UserResponse getUserById(UUID userId);
}
