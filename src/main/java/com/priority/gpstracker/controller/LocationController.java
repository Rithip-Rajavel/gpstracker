package com.priority.gpstracker.controller;

import com.priority.gpstracker.dto.LocationRequest;
import com.priority.gpstracker.dto.ReminderResponse;
import com.priority.gpstracker.service.ReminderService;
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

import java.util.List;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@Tag(name = "Location Services", description = "APIs for GPS location checking and reminder triggering")
public class LocationController {

   private final ReminderService reminderService;

   @Operation(
           summary = "Check location for reminders",
           description = "Checks if the user's current GPS location is within any reminder radius and returns triggered reminders"
   )
   @ApiResponses(value = {
           @ApiResponse(
                   responseCode = "200",
                   description = "Location check completed",
                   content = @Content(
                           mediaType = "application/json",
                           schema = @Schema(implementation = ReminderResponse.class)
                   )
           ),
           @ApiResponse(responseCode = "400", description = "Invalid location data")
   })
   @PostMapping("/check")
   public List<ReminderResponse> checkLocation(
           @Parameter(description = "User's current GPS location", required = true)
           @Valid @RequestBody LocationRequest request) {
       return reminderService.checkLocation(request);
   }
}
