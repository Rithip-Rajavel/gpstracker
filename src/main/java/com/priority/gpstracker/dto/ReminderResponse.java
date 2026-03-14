package com.priority.gpstracker.dto;

import java.util.UUID;

public record ReminderResponse(

       UUID id,
       String title,
       String message
) {}
