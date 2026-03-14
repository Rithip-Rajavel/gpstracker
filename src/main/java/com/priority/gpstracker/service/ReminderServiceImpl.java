package com.priority.gpstracker.service;

import com.priority.gpstracker.dto.LocationRequest;
import com.priority.gpstracker.dto.ReminderRequest;
import com.priority.gpstracker.dto.ReminderResponse;
import com.priority.gpstracker.model.Reminder;
import com.priority.gpstracker.repository.ReminderRepository;
import com.priority.gpstracker.util.GeoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReminderServiceImpl implements ReminderService {

   private final ReminderRepository reminderRepository;

   @Override
   public ReminderResponse createReminder(ReminderRequest request) {

       Reminder reminder = Reminder.builder()
               .title(request.title())
               .message(request.message())
               .latitude(request.latitude())
               .longitude(request.longitude())
               .radius(request.radius())
               .isTriggered(false)
               .createdAt(LocalDateTime.now())
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
   public List<ReminderResponse> getAllReminders() {

       return reminderRepository.findAll()
               .stream()
               .map(r -> new ReminderResponse(
                       r.getId(),
                       r.getTitle(),
                       r.getMessage()
               ))
               .toList();
   }

   @Override
   public void deleteReminder(java.util.UUID id) {
       reminderRepository.deleteById(id);
   }

   @Override
   public List<ReminderResponse> checkLocation(LocationRequest request) {

       double userLat = request.latitude();
       double userLon = request.longitude();

       double latRange = 0.01;
       double lonRange = 0.01;

       List<Reminder> nearbyReminders =
               reminderRepository.findByIsTriggeredFalseAndLatitudeBetweenAndLongitudeBetween(
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
