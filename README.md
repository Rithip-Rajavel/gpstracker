# GPS Tracker API

A RESTful API for GPS-based location reminder system. This API allows users to create location-based reminders that trigger when they reach a specific GPS coordinate within a defined radius.

## 🚀 Features

- **Location-based Reminders**: Create reminders tied to specific GPS coordinates
- **Geofencing**: Automatic triggering when entering defined radius
- **RESTful API**: Clean and intuitive API design
- **Swagger Documentation**: Interactive API documentation
- **PostgreSQL Integration**: Persistent data storage
- **Performance Optimized**: Bounding box optimization for efficient queries

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.5.9
- **Java**: 21
- **Database**: PostgreSQL
- **Documentation**: Swagger/OpenAPI 3.0
- **Build Tool**: Maven
- **Additional**: Lombok, Spring Data JPA, Validation

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- IDE (IntelliJ, Eclipse, VS Code)

## 🚀 Quick Start

### 1. Database Setup

Create a PostgreSQL database named `gps`:

```sql
CREATE DATABASE gps;
```

### 2. Configuration

The application is configured to run on:
- **Port**: 5078
- **Context Path**: `/api/gps`
- **Database**: PostgreSQL on localhost:5432/gps
- **Default Credentials**: postgres/postgres

Update `src/main/resources/application.yaml` if needed:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gps
    username: postgres
    password: postgres
```

### 3. Run the Application

```bash
# Clone the repository
git clone <repository-url>
cd gpstracker

# Build and run
mvn clean install
mvn spring-boot:run
```

### 4. Access Swagger UI

Open your browser and navigate to:
```
http://localhost:5078/api/gps/swagger-ui.html
```

## 📚 API Documentation

### Base URL
```
http://localhost:5078/api/gps
```

### Endpoints

#### 1. Create Reminder
Creates a new GPS-based reminder.

**Endpoint**: `POST /api/reminders`

**Request Body**:
```json
{
  "title": "Buy Milk",
  "message": "Buy milk from supermarket",
  "latitude": 11.341036,
  "longitude": 77.717164,
  "radius": 200
}
```

**Response**:
```json
{
  "id": "7dfb4c3f-3d13-44de-8a6c-4b0bb0c7d62e",
  "title": "Buy Milk",
  "message": "Buy milk from supermarket"
}
```

**CURL Example**:
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

#### 2. Get All Reminders
Retrieves all stored reminders.

**Endpoint**: `GET /api/reminders`

**Response**:
```json
[
  {
    "id": "7dfb4c3f-3d13-44de-8a6c-4b0bb0c7d62e",
    "title": "Buy Milk",
    "message": "Buy milk from supermarket"
  },
  {
    "id": "3e3b9c02-61fa-4b4f-a48e-3bbdfcb7c8ff",
    "title": "Pick Parcel",
    "message": "Collect parcel from courier office"
  }
]
```

**CURL Example**:
```bash
curl --location 'http://localhost:5078/api/gps/api/reminders'
```

#### 3. Delete Reminder
Deletes a reminder by ID.

**Endpoint**: `DELETE /api/reminders/{id}`

**Path Variables**:
- `id`: UUID of the reminder to delete

**Response**: HTTP 200 OK (empty body)

**CURL Example**:
```bash
curl --location --request DELETE \
'http://localhost:5078/api/gps/api/reminders/7dfb4c3f-3d13-44de-8a6c-4b0bb0c7d62e'
```

#### 4. Check Location
Core API for GPS location checking. Returns reminders that should trigger.

**Endpoint**: `POST /api/location/check`

**Request Body**:
```json
{
  "latitude": 11.341040,
  "longitude": 77.717170
}
```

**Response (Reminder Triggered)**:
```json
[
  {
    "id": "7dfb4c3f-3d13-44de-8a6c-4b0bb0c7d62e",
    "title": "Buy Milk",
    "message": "Buy milk from supermarket"
  }
]
```

**Response (No Reminder Triggered)**:
```json
[]
```

**CURL Example**:
```bash
curl --location 'http://localhost:5078/api/gps/api/location/check' \
--header 'Content-Type: application/json' \
--data '{
  "latitude": 11.341040,
  "longitude": 77.717170
}'
```

## 🔄 Example Flow

### Demo Scenario

1. **Create a Reminder**
   ```bash
   POST /api/reminders
   {
     "title": "Buy Milk",
     "message": "Buy milk from supermarket",
     "latitude": 11.341036,
     "longitude": 77.717164,
     "radius": 200
   }
   ```

2. **Check Location Outside Radius**
   ```bash
   POST /api/location/check
   {
     "latitude": 11.341000,
     "longitude": 77.717000
   }
   Response: []
   ```

3. **Check Location Inside Radius**
   ```bash
   POST /api/location/check
   {
     "latitude": 11.341040,
     "longitude": 77.717170
   }
   Response: [{"id": "...", "title": "Buy Milk", "message": "Buy milk from supermarket"}]
   ```

4. **Reminder Becomes Triggered**
   - After triggering, `isTriggered` is set to `true`
   - Subsequent location checks won't return the same reminder

## 🗄️ Database Schema

### Reminders Table

```sql
CREATE TABLE reminders (
   id UUID PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   message TEXT NOT NULL,
   latitude DOUBLE PRECISION NOT NULL,
   longitude DOUBLE PRECISION NOT NULL,
   radius INTEGER NOT NULL,
   is_triggered BOOLEAN NOT NULL,
   created_at TIMESTAMP NOT NULL
);
```

**Field Descriptions**:
- `id`: Unique identifier (UUID)
- `title`: Reminder title
- `message`: Detailed reminder message
- `latitude`: GPS latitude coordinate
- `longitude`: GPS longitude coordinate
- `radius`: Trigger radius in meters (e.g., 200 = 200m)
- `is_triggered`: Whether reminder has been triggered
- `created_at`: Timestamp when reminder was created

## 🧮 GPS Distance Calculation

The system uses the **Haversine Formula** to calculate the great-circle distance between two points on a sphere:

```
distance = 2 * R * asin(√(sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)))
```

Where:
- R = Earth's radius (6,371,000 meters)
- φ1, φ2 = Latitude of points 1 and 2 in radians
- Δφ = Difference in latitudes
- Δλ = Difference in longitudes

## ⚡ Performance Optimization

### Bounding Box Optimization

Instead of checking all reminders, the system first filters reminders within a bounding box:

```java
// Fetch only nearby reminders (0.01° ≈ 1.1km)
List<Reminder> nearbyReminders = repository.findByIsTriggeredFalseAndLatitudeBetweenAndLongitudeBetween(
    userLat - 0.01, userLat + 0.01,
    userLon - 0.01, userLon + 0.01
);

// Then apply exact distance calculation
List<Reminder> triggered = nearbyReminders.stream()
    .filter(r -> GeoUtils.isWithinRadius(userLat, userLon, r.getLatitude(), r.getLongitude(), r.getRadius()))
    .collect(toList());
```

This reduces database load and improves scalability significantly.

## 🧪 Testing

### Using Swagger UI

1. Navigate to `http://localhost:5078/api/gps/swagger-ui.html`
2. Expand any endpoint
3. Click "Try it out"
4. Fill in the request parameters
5. Click "Execute"

### Using Postman

Import the following collection or manually create requests:

**Collection Variables**:
- `baseUrl`: `http://localhost:5078/api/gps`

**Requests**:
- Create Reminder: `POST {{baseUrl}}/api/reminders`
- Get All Reminders: `GET {{baseUrl}}/api/reminders`
- Delete Reminder: `DELETE {{baseUrl}}/api/reminders/{id}`
- Check Location: `POST {{baseUrl}}/api/location/check`

## 🔧 Configuration Options

### Application Properties

Key configuration options in `application.yaml`:

```yaml
server:
  port: 5078                    # Server port
  servlet:
    context-path: /api/gps      # API context path

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gps
    username: postgres
    password: postgres
  
  jpa:
    hibernate:
      ddl-auto: update          # Auto-create/update schema
    show-sql: true              # Show SQL in logs
    properties:
      hibernate:
        format_sql: true        # Format SQL nicely

springdoc:
  swagger-ui:
    path: /swagger-ui.html      # Swagger UI path
  api-docs:
    path: /v3/api-docs          # OpenAPI docs path
```

## 🚨 Error Handling

The API returns standard HTTP status codes:

- `200 OK`: Request successful
- `400 Bad Request`: Invalid input data
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

Error responses include descriptive messages:

```json
{
  "timestamp": "2024-01-01T12:00:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for argument [0]",
  "path": "/api/reminders"
}
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Email: support@gpstracker.com
- Issues: [GitHub Issues](https://github.com/your-repo/issues)

---

**Note**: This is a college-level project focused on GPS-based reminder functionality. The architecture is intentionally simple to demonstrate core concepts while maintaining clean code practices.
