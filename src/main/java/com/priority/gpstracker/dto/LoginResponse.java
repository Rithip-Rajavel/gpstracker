package com.priority.gpstracker.dto;

import java.util.UUID;

public record LoginResponse(

       String token,
       String type,
       UUID userId,
       String email,
       String name
) {}
