package com.priority.gpstracker.service;

import com.priority.gpstracker.dto.LoginRequest;
import com.priority.gpstracker.dto.LoginResponse;
import com.priority.gpstracker.dto.SignupRequest;
import com.priority.gpstracker.dto.UserResponse;
import com.priority.gpstracker.model.User;
import com.priority.gpstracker.repository.UserRepository;
import com.priority.gpstracker.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final JwtUtil jwtUtil;

   @Override
   public UserResponse signup(SignupRequest request) {
       if (userRepository.existsByEmail(request.email())) {
           throw new RuntimeException("Email already exists");
       }

       User user = User.builder()
               .email(request.email())
               .password(passwordEncoder.encode(request.password()))
               .name(request.name())
               .createdAt(LocalDateTime.now())
               .build();

       User savedUser = userRepository.save(user);

       return new UserResponse(
               savedUser.getId(),
               savedUser.getEmail(),
               savedUser.getName()
       );
   }

   @Override
   @Transactional(readOnly = true)
   public LoginResponse login(LoginRequest request) {
       User user = userRepository.findByEmail(request.email())
               .orElseThrow(() -> new RuntimeException("Invalid credentials"));

       if (!passwordEncoder.matches(request.password(), user.getPassword())) {
           throw new RuntimeException("Invalid credentials");
       }

       String token = jwtUtil.generateToken(user.getEmail(), user.getId());

       return new LoginResponse(
               token,
               "Bearer",
               user.getId(),
               user.getEmail(),
               user.getName()
       );
   }

   @Override
   @Transactional(readOnly = true)
   public UserResponse getUserById(UUID userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));

       return new UserResponse(
               user.getId(),
               user.getEmail(),
               user.getName()
       );
   }
}
