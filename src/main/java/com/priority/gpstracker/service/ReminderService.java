package com.priority.gpstracker.service;

import com.priority.gpstracker.dto.LocationRequest;
import com.priority.gpstracker.dto.ReminderRequest;
import com.priority.gpstracker.dto.ReminderResponse;

import java.util.List;
import java.util.UUID;

public interface ReminderService {

   ReminderResponse createReminder(ReminderRequest request);

   List<ReminderResponse> getAllReminders();

   void deleteReminder(UUID id);

   List<ReminderResponse> checkLocation(LocationRequest request);
}
