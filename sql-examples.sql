# Example SQL queries for testing GPS Tracker

## Create Database
```sql
CREATE DATABASE gps;
```

## Connect to GPS Database
```sql
\c gps;
```

## View Reminders Table Structure
```sql
\d reminders;
```

## Insert Test Reminder
```sql
INSERT INTO reminders (id, title, message, latitude, longitude, radius, is_triggered, created_at)
VALUES (
    gen_random_uuid(),
    'Buy Milk',
    'Buy milk from supermarket',
    11.341036,
    77.717164,
    200,
    false,
    NOW()
);
```

## View All Reminders
```sql
SELECT * FROM reminders;
```

## View Only Active Reminders
```sql
SELECT * FROM reminders WHERE is_triggered = false;
```

## View Reminders in Specific Area (Bounding Box)
```sql
SELECT * FROM reminders 
WHERE latitude BETWEEN 11.340 AND 11.342 
  AND longitude BETWEEN 77.716 AND 77.718
  AND is_triggered = false;
```

## Calculate Distance Between Two Points
```sql
SELECT 
    2 * 6371000 * ASIN(SQRT(
        POWER(SIN(RADIANS(11.341040 - 11.341036) / 2), 2) +
        COS(RADIANS(11.341036)) * COS(RADIANS(11.341040)) *
        POWER(SIN(RADIANS(77.717170 - 77.717164) / 2), 2)
    )) AS distance_in_meters;
```

## Mark Reminder as Triggered
```sql
UPDATE reminders 
SET is_triggered = true 
WHERE id = 'your-reminder-id-here';
```

## Delete All Reminders
```sql
DELETE FROM reminders;
```

## Delete Specific Reminder
```sql
DELETE FROM reminders WHERE id = 'your-reminder-id-here';
```

## Count Active vs Triggered Reminders
```sql
SELECT 
    is_triggered,
    COUNT(*) as count
FROM reminders 
GROUP BY is_triggered;
```
