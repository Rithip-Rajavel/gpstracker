package com.priority.gpstracker.controller;

import com.priority.gpstracker.dto.ReminderRequest;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
@Tag(name = "Reminder Management", description = "APIs for managing GPS reminders")
public class ReminderController {

   private final ReminderService reminderService;

   @Operation(
           summary = "Create a new GPS reminder",
           description = "Creates a new reminder with location and radius for GPS-based triggering"
   )
   @ApiResponses(value = {
           @ApiResponse(
                   responseCode = "200",
                   description = "Reminder created successfully",
                   content = @Content(
                           mediaType = "application/json",
                           schema = @Schema(implementation = ReminderResponse.class)
                   )
           ),
           @ApiResponse(responseCode = "400", description = "Invalid input data")
   })
   @PostMapping
   public ReminderResponse createReminder(
           @Parameter(description = "Reminder details to create", required = true)
           @Valid @RequestBody ReminderRequest request) {
       return reminderService.createReminder(request);
   }

   @Operation(
           summary = "Get all reminders",
           description = "Retrieves all reminders stored in the database"
   )
   @ApiResponses(value = {
           @ApiResponse(
                   responseCode = "200",
                   description = "Reminders retrieved successfully",
                   content = @Content(
                           mediaType = "application/json",
                           schema = @Schema(implementation = ReminderResponse.class)
                   )
           )
   })
   @GetMapping
   public List<ReminderResponse> getAllReminders() {
       return reminderService.getAllReminders();
   }

   @Operation(
           summary = "Delete a reminder",
           description = "Deletes a reminder by its unique identifier"
   )
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Reminder deleted successfully"),
           @ApiResponse(responseCode = "404", description = "Reminder not found")
   })
   @DeleteMapping("/{id}")
   public void deleteReminder(
           @Parameter(description = "ID of the reminder to delete", required = true)
           @PathVariable UUID id) {
       reminderService.deleteReminder(id);
   }
}
