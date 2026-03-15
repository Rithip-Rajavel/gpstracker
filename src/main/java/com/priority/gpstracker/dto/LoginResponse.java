package com.priority.gpstracker.dto;

import java.util.UUID;

public record LoginResponse(
       String token,
       UserResponse user
) {}
