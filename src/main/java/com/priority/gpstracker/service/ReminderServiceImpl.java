package com.priority.gpstracker.service;

import com.priority.gpstracker.dto.LocationRequest;
import com.priority.gpstracker.dto.ReminderRequest;
import com.priority.gpstracker.dto.ReminderResponse;
import com.priority.gpstracker.model.Reminder;
import com.priority.gpstracker.model.User;
import com.priority.gpstracker.repository.ReminderRepository;
import com.priority.gpstracker.repository.UserRepository;
import com.priority.gpstracker.util.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReminderServiceImpl implements ReminderService {

   private final ReminderRepository reminderRepository;
   private final UserRepository userRepository;

   @Override
   public ReminderResponse createReminder(ReminderRequest request, UUID userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));

       Reminder reminder = Reminder.builder()
               .title(request.title())
               .message(request.message())
               .latitude(request.latitude())
               .longitude(request.longitude())
               .radius(request.radius())
               .isTriggered(false)
               .createdAt(LocalDateTime.now())
               .user(user)
               .build();

       Reminder saved = reminderRepository.save(reminder);

       return new ReminderResponse(
               saved.getId(),
               saved.getTitle(),
               saved.getMessage()
       );
   }

   @Override
   @Transactional(readOnly = true)
   public List<ReminderResponse> getAllReminders(UUID userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));

       return reminderRepository.findByUser(user)
               .stream()
               .map(r -> new ReminderResponse(
                       r.getId(),
                       r.getTitle(),
                       r.getMessage()
               ))
               .toList();
   }

   @Override
   public void deleteReminder(UUID id, UUID userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));

       Reminder reminder = reminderRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Reminder not found"));

       if (!reminder.getUser().getId().equals(userId)) {
           throw new RuntimeException("Access denied");
       }

       reminderRepository.delete(reminder);
   }

   @Override
   public List<ReminderResponse> checkLocation(LocationRequest request, UUID userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));

       double userLat = request.latitude();
       double userLon = request.longitude();

       double latRange = 0.01;
       double lonRange = 0.01;

       List<Reminder> nearbyReminders =
               reminderRepository.findByUserAndIsTriggeredFalseAndLatitudeBetweenAndLongitudeBetween(
                       user,
                       userLat - latRange,
                       userLat + latRange,
                       userLon - lonRange,
                       userLon + lonRange
               );

       List<Reminder> triggeredReminders = nearbyReminders.stream()
               .filter(reminder -> GeoUtils.isWithinRadius(
                       userLat,
                       userLon,
                       reminder.getLatitude(),
                       reminder.getLongitude(),
                       reminder.getRadius()
               ))
               .toList();

       triggeredReminders.forEach(reminder -> reminder.setTriggered(true));

       reminderRepository.saveAll(triggeredReminders);

       return triggeredReminders.stream()
               .map(r -> new ReminderResponse(
                       r.getId(),
                       r.getTitle(),
                       r.getMessage()
               ))
               .toList();
   }
}
