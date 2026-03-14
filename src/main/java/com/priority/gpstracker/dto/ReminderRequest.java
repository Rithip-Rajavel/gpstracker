package com.priority.gpstracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReminderRequest(

       @NotBlank
       String title,

       @NotBlank
       String message,

       @NotNull
       Double latitude,

       @NotNull
       Double longitude,

       @NotNull
       Integer radius
) {}
