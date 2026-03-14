package com.priority.gpstracker.dto;

import jakarta.validation.constraints.NotNull;

public record LocationRequest(

       @NotNull
       Double latitude,

       @NotNull
       Double longitude
) {}
