package com.priority.gpstracker.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReminderResponse(
       UUID id,
       String title,
       String message,
       Double latitude,
       Double longitude,
       Double radius,
       Boolean isTriggered,
       LocalDateTime createdAt
) {}
