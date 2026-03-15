# GPS Tracker API Documentation

## Overview
The GPS Tracker API is a RESTful service for location-based reminder management with user authentication. Users can create personal reminders that trigger when they reach specific GPS coordinates within a defined radius.

**Base URL:** `http://localhost:5078/api/gps`

## Authentication
The API uses JWT (JSON Web Token) for authentication. Include the token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Database Setup
Create PostgreSQL database:
```sql
CREATE DATABASE gps;
```

---

## Authentication APIs

### 1. User Signup
**Endpoint:** `POST /api/auth/signup`

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "name": "John Doe"
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "user@example.com",
  "name": "John Doe"
}
```

**CURL Example:**
```bash
curl -X POST http://localhost:5078/api/gps/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "name": "John Doe"
  }'
```

---

### 2. User Login
**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwidXNlcklkIjoiNTUwZTg0MDAtZTI5Yi00MWQ0LWE3MTYtNDQ2NjU1NDQwMDAwIiwiaWF0IjoxNjE2MjM5MDIyLCJleHAiOjE2MTYzMjU0MjJ9.signature",
  "type": "Bearer",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "email": "user@example.com",
  "name": "John Doe"
}
```

**CURL Example:**
```bash
curl -X POST http://localhost:5078/api/gps/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

---

### 3. Get User Profile
**Endpoint:** `GET /api/auth/profile?userId={userId}`

**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "email": "user@example.com",
  "name": "John Doe"
}
```

---

## Reminder Management APIs

### 1. Create Reminder
**Endpoint:** `POST /api/reminders`

**Headers:** 
```
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "Buy Milk",
  "message": "Buy milk from supermarket",
  "latitude": 11.341036,
  "longitude": 77.717164,
  "radius": 200
}
```

**Response:**
```json
{
  "id": "7dfb4c3f-3d13-44de-8a6c-4b0bb0c7d62e",
  "title": "Buy Milk",
  "message": "Buy milk from supermarket"
}
```

**CURL Example:**
```bash
curl -X POST http://localhost:5078/api/gps/api/reminders \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Buy Milk",
    "message": "Buy milk from supermarket",
    "latitude": 11.341036,
    "longitude": 77.717164,
    "radius": 200
  }'
```

---

### 2. Get All User Reminders
**Endpoint:** `GET /api/reminders`

**Headers:** `Authorization: Bearer <token>`

**Response:**
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

**CURL Example:**
```bash
curl -X GET http://localhost:5078/api/gps/api/reminders \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

### 3. Delete Reminder
**Endpoint:** `DELETE /api/reminders/{id}`

**Headers:** `Authorization: Bearer <token>`

**Response:** HTTP 200 OK (Empty body)

**CURL Example:**
```bash
curl -X DELETE http://localhost:5078/api/gps/api/reminders/7dfb4c3f-3d13-44de-8a6c-4b0bb0c7d62e \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## Location Services APIs

### 1. Check Location for Reminders
**Endpoint:** `POST /api/location/check`

**Headers:** 
```
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "latitude": 11.341040,
  "longitude": 77.717170
}
```

**Response (Reminders Triggered):**
```json
[
  {
    "id": "7dfb4c3f-3d13-44de-8a6c-4b0bb0c7d62e",
    "title": "Buy Milk",
    "message": "Buy milk from supermarket"
  }
]
```

**Response (No Reminders Triggered):**
```json
[]
```

**CURL Example:**
```bash
curl -X POST http://localhost:5078/api/gps/api/location/check \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "latitude": 11.341040,
    "longitude": 77.717170
  }'
```

---

## Error Responses

### 401 Unauthorized
```json
{
  "timestamp": "2024-01-20T10:30:00Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "JWT token is missing or invalid"
}
```

### 400 Bad Request
```json
{
  "timestamp": "2024-01-20T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid input data"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-20T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Reminder not found"
}
```

---

## Database Schema

### Users Table
```sql
CREATE TABLE users (
   id UUID PRIMARY KEY,
   email VARCHAR(255) UNIQUE NOT NULL,
   password VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   created_at TIMESTAMP NOT NULL
);
```

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
   created_at TIMESTAMP NOT NULL,
   user_id UUID NOT NULL,
   FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## Swagger UI
Access interactive API documentation at:
`http://localhost:5078/api/gps/swagger-ui/index.html`

---

## Example Flow (React Native Integration)

1. **User Registration**
   - Mobile app sends signup request
   - Backend creates user account
   - User can now login

2. **Authentication**
   - User logs in with email/password
   - Backend returns JWT token
   - Mobile app stores token securely

3. **Create Reminder**
   - User selects location on map
   - Mobile app sends reminder data with JWT token
   - Backend stores reminder for that user

4. **Location Tracking**
   - Mobile app periodically sends GPS location with JWT token
   - Backend checks user's reminders only
   - Returns triggered reminders for that user
   - Mobile app shows notifications

---

## Security Features

- **JWT Authentication**: Secure token-based authentication
- **Password Hashing**: BCrypt encryption for passwords
- **User Isolation**: Each user can only access their own reminders
- **Token Validation**: Automatic token expiration and validation
- **CORS Protection**: Configured for mobile app access
