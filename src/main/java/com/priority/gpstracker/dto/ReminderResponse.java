package com.priority.gpstracker.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReminderResponse(
       UUID id,
       String title,
       String message,
       double latitude,
       double longitude,
       int radius,
       boolean isTriggered,
       LocalDateTime createdAt
) {}
