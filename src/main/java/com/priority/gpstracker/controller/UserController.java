package com.priority.gpstracker.controller;

import com.priority.gpstracker.dto.LoginRequest;
import com.priority.gpstracker.dto.LoginResponse;
import com.priority.gpstracker.dto.SignupRequest;
import com.priority.gpstracker.dto.UserResponse;
import com.priority.gpstracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for user authentication and management")
public class UserController {

   private final UserService userService;

   @Operation(
           summary = "User signup",
           description = "Registers a new user account"
   )
   @ApiResponses(value = {
           @ApiResponse(
                   responseCode = "200",
                   description = "User registered successfully",
                   content = @Content(
                           mediaType = "application/json",
                           schema = @Schema(implementation = UserResponse.class)
                   )
           ),
           @ApiResponse(responseCode = "400", description = "Invalid input data")
   })
   @PostMapping("/signup")
   public UserResponse signup(
           @Parameter(description = "User registration details", required = true)
           @Valid @RequestBody SignupRequest request) {
       return userService.signup(request);
   }

   @Operation(
           summary = "User login",
           description = "Authenticates user and returns JWT token"
   )
   @ApiResponses(value = {
           @ApiResponse(
                   responseCode = "200",
                   description = "Login successful",
                   content = @Content(
                           mediaType = "application/json",
                           schema = @Schema(implementation = LoginResponse.class)
                   )
           ),
           @ApiResponse(responseCode = "401", description = "Invalid credentials")
   })
   @PostMapping("/login")
   public LoginResponse login(
           @Parameter(description = "User login credentials", required = true)
           @Valid @RequestBody LoginRequest request) {
       return userService.login(request);
   }

   @Operation(
           summary = "Get user profile",
           description = "Retrieves user profile information"
   )
   @ApiResponses(value = {
           @ApiResponse(
                   responseCode = "200",
                   description = "Profile retrieved successfully",
                   content = @Content(
                           mediaType = "application/json",
                           schema = @Schema(implementation = UserResponse.class)
                   )
           ),
           @ApiResponse(responseCode = "404", description = "User not found")
   })
   @GetMapping("/profile")
   public UserResponse getProfile(
           @Parameter(description = "User ID", required = true)
           @RequestParam UUID userId) {
       return userService.getUserById(userId);
   }
}
