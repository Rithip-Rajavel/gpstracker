# GPS Tracker API

## Quick Test Commands

### 1. Create Reminder
```bash
curl --location 'http://localhost:5078/api/gps/api/reminders' \
--header 'Content-Type: application/json' \
--data '{
  "title": "Buy Milk",
  "message": "Buy milk from supermarket",
  "latitude": 11.341036,
  "longitude": 77.717164,
  "radius": 200
}'
```

### 2. Get All Reminders
```bash
curl --location 'http://localhost:5078/api/gps/api/reminders'
```

### 3. Check Location (Inside Radius)
```bash
curl --location 'http://localhost:5078/api/gps/api/location/check' \
--header 'Content-Type: application/json' \
--data '{
  "latitude": 11.341040,
  "longitude": 77.717170
}'
```

### 4. Check Location (Outside Radius)
```bash
curl --location 'http://localhost:5078/api/gps/api/location/check' \
--header 'Content-Type: application/json' \
--data '{
  "latitude": 11.341000,
  "longitude": 77.717000
}'
```

### 5. Delete Reminder
```bash
curl --location --request DELETE \
'http://localhost:5078/api/gps/api/reminders/YOUR_REMINDER_ID'
```

## Access Swagger UI
http://localhost:5078/api/gps/swagger-ui.html

## Database Setup
```sql
CREATE DATABASE gps;
```

## Run Application
```bash
./mvnw spring-boot:run
```
