# GPS Tracker - Test Data Examples

## Sample Reminders for Testing

### 1. Shopping Mall Reminder
```json
{
  "title": "Buy Groceries",
  "message": "Get vegetables and fruits from the mall",
  "latitude": 11.341036,
  "longitude": 77.717164,
  "radius": 200
}
```

### 2. Office Reminder
```json
{
  "title": "Submit Report",
  "message": "Submit weekly report to manager",
  "latitude": 11.342500,
  "longitude": 77.718500,
  "radius": 100
}
```

### 3. Gym Reminder
```json
{
  "title": "Workout",
  "message": "Don't forget to do cardio today",
  "latitude": 11.340000,
  "longitude": 77.716000,
  "radius": 150
}
```

### 4. Pharmacy Reminder
```json
{
  "title": "Buy Medicine",
  "message": "Pick up prescription from pharmacy",
  "latitude": 11.343000,
  "longitude": 77.719000,
  "radius": 50
}
```

## Sample Location Check Requests

### Location 1: Near Shopping Mall (Should Trigger)
```json
{
  "latitude": 11.341040,
  "longitude": 77.717170
}
```

### Location 2: Near Office (Should Trigger)
```json
{
  "latitude": 11.342480,
  "longitude": 77.718520
}
```

### Location 3: Far from All Reminders (Should Not Trigger)
```json
{
  "latitude": 11.350000,
  "longitude": 77.720000
}
```

## Test Scenarios

### Scenario 1: Single Reminder Trigger
1. Create shopping mall reminder
2. Check location near mall
3. Verify reminder is returned
4. Check same location again
5. Verify no reminder returned (already triggered)

### Scenario 2: Multiple Reminders Trigger
1. Create multiple reminders in same area
2. Check location that covers multiple radii
3. Verify all applicable reminders are returned
4. Check location again
5. Verify no reminders returned

### Scenario 3: No Reminders Trigger
1. Create reminders
2. Check location far from all reminders
3. Verify empty response

### Scenario 4: Boundary Testing
1. Create reminder with 100m radius
2. Check location exactly 99m away - should trigger
3. Check location exactly 101m away - should not trigger
4. Check location exactly 100m away - should trigger

## Performance Testing

### Large Dataset Test
Create 1000+ reminders and test location checking performance.

```bash
# For loop to create multiple reminders (bash)
for i in {1..1000}; do
  curl --location 'http://localhost:5078/api/gps/api/reminders' \
  --header 'Content-Type: application/json' \
  --data "{
    \"title\": \"Test Reminder $i\",
    \"message\": \"Test message $i\",
    \"latitude\": $((11 + RANDOM)),
    \"longitude\": $((77 + RANDOM)),
    \"radius\": $((100 + RANDOM % 400))
  }"
done
```

## Edge Cases

### Invalid Coordinates
```json
{
  "latitude": 91.0,
  "longitude": 181.0
}
```

### Zero Radius
```json
{
  "title": "Point Reminder",
  "message": "Exact location reminder",
  "latitude": 11.341036,
  "longitude": 77.717164,
  "radius": 0
}
```

### Very Large Radius
```json
{
  "title": "City Wide Reminder",
  "message": "Reminder for entire city",
  "latitude": 11.341036,
  "longitude": 77.717164,
  "radius": 50000
}
```
