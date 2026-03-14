package com.priority.gpstracker.repository;

import com.priority.gpstracker.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, UUID> {

   List<Reminder> findByIsTriggeredFalse();

   List<Reminder> findByIsTriggeredFalseAndLatitudeBetweenAndLongitudeBetween(
           double minLat,
           double maxLat,
           double minLon,
           double maxLon
   );
}
