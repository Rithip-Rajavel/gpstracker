package com.priority.gpstracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(

       @NotBlank
       @Email
       String email,

       @NotBlank
       @Size(min = 6, message = "Password must be at least 6 characters")
       String password,

       @NotBlank
       @Size(min = 2, message = "Name must be at least 2 characters")
       String name
) {}
