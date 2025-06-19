# Clinic Management System

This project provides a simple clinic management backend built with Spring Boot.  

## WebSocket Authentication

WebSocket connections to `/ws` now support JWT based authentication. A custom
`JwtHandshakeInterceptor` extracts the `Authorization` header during the initial
handshake and creates a `UserPrincipleAuthenticationToken` which is stored in the
handshake attributes. A `JwtChannelInterceptor` also validates `CONNECT` frames
so STOMP messages have an authenticated principal.

Clients should include a standard `Authorization: Bearer <token>` header when
opening the WebSocket connection or sending the STOMP `CONNECT` frame.

## API Overview

### Authentication

- **POST /auth/register**  
  Registers a new user.  
  **Request**: `UserDto`  
  **Response**: Created `UserDto`

- **POST /auth/login**  
  Logs in a user.  
  **Request**: `LoginRequest`  
  **Response**: `TokenResponse`

- **POST /auth/refresh**  
  Refreshes JWT token.  
  **Request**: `RefreshTokenRequest`  
  **Response**: `TokenResponse`

### Doctors

- **GET /doctors/{id}**  
  Get doctor details.  
  **Response**: `DoctorDto`

- **PUT /doctors/{id}/address**  
  Update doctor address.  
  **Request**: `AddressDto`  
  **Response**: Updated `DoctorDto`

- **POST /doctors/{id}/specializations**  
  Add specializations.  
  **Request**: `SpecializationsDto`  
  **Response**: Updated `DoctorDto`

- ... _(More endpoints covered in full documentation)_

### Appointments

- **POST /appointments/{id}**  
  Create an appointment.  
  **Request**: `AppointmentDto`  
  **Response**: Created `AppointmentDto`

- **GET /appointments/{id}/date-range**  
  Get appointments in a date range.  
  **Request**: `LocalDateTimeBlock`  
  **Response**: List of `AppointmentDto`

### Patients

- **PUT /patients/{patientId}/drugs/{drugId}**  
  Update patient drug.  
  **Request**: `PatientDrugDto`  
  **Response**: Updated `PatientDto`

- **PUT /patients/{patientId}/analyses/{analyseId}**  
  Update patient analysis.  
  **Request**: `AnalyseDto`  
  **Response**: Updated `PatientDto`

## Full API Documentation

See [`Full_API_Documentation.pdf`](./Full_API_Documentation.pdf) for a comprehensive list of all available endpoints, request/response structures, and example JSON shapes.
## Docker

A `Dockerfile` and `docker-compose.yml` are provided to run the application with MySQL.
Build and start both containers using:

```bash
docker-compose up --build
```

This will expose the API on [http://localhost:8080](http://localhost:8080) and persist uploads to the `uploads` directory on the host.
