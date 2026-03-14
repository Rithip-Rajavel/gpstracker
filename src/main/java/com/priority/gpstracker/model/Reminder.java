package com.priority.gpstracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reminders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(nullable = false)
   private String title;

   @Column(nullable = false)
   private String message;

   @Column(nullable = false)
   private double latitude;

   @Column(nullable = false)
   private double longitude;

   @Column(nullable = false)
   private int radius;

   @Column(name = "is_triggered", nullable = false)
   private boolean isTriggered;

   @Column(name = "created_at", nullable = false)
   private LocalDateTime createdAt;
}
