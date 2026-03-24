# API Documentation - Patient Appointment Management System

## Base URL
```
http://localhost:8080/api
```

## Authentication
Currently, the API does not require authentication. For production, implement JWT or OAuth2.

---

## Response Format

All responses follow a standard format:

### Success Response
```json
{
  "message": "Operation successful",
  "data": { /* response payload */ },
  "status": "success"
}
```

### Error Response
```json
{
  "statusCode": 400,
  "message": "Error description",
  "timestamp": "2024-03-19T10:30:00",
  "path": "/api/endpoint"
}
```

---

## Patient Management Endpoints

### Create Patient
**POST** `/patients`

Creates a new patient record.

**Request Body:**
```json
{
  "firstName": "string",
  "lastName": "string",
  "age": 0,
  "phoneNumber": "string",
  "email": "string",
  "medicalHistory": "string"
}
```

**Response:** `201 Created`
```json
{
  "message": "Patient created successfully",
  "data": {
    "patientId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "age": 30,
    "phoneNumber": "1234567890",
    "email": "john@example.com",
    "medicalHistory": "No allergies",
    "createdAt": "2024-03-19T10:30:00",
    "updatedAt": "2024-03-19T10:30:00"
  },
  "status": "success"
}
```

**Validation Rules:**
- `firstName` - Required, non-empty string
- `lastName` - Required, non-empty string
- `age` - Required, must be >= 0
- `phoneNumber` - Required, 10-15 digits
- `email` - Optional, must be valid email format

---

### Get All Patients
**GET** `/patients`

Retrieves all patients in the system.

**Response:** `200 OK`
```json
{
  "data": [
    {
      "patientId": 1,
      "firstName": "John",
      "lastName": "Doe",
      "age": 30,
      "phoneNumber": "1234567890",
      "email": "john@example.com",
      "medicalHistory": "No allergies",
      "createdAt": "2024-03-19T10:30:00",
      "updatedAt": "2024-03-19T10:30:00"
    }
  ],
  "total": 5,
  "status": "success"
}
```

---

### Get Patient by ID
**GET** `/patients/{patientId}`

Retrieves a specific patient by ID.

**Path Parameters:**
- `patientId` (Long) - The patient's unique identifier

**Response:** `200 OK`
```json
{
  "data": {
    "patientId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "age": 30,
    "phoneNumber": "1234567890",
    "email": "john@example.com",
    "medicalHistory": "No allergies",
    "createdAt": "2024-03-19T10:30:00",
    "updatedAt": "2024-03-19T10:30:00"
  },
  "status": "success"
}
```

**Error Response:** `404 Not Found`
```json
{
  "statusCode": 404,
  "message": "Patient not found with ID: 999",
  "timestamp": "2024-03-19T10:35:00",
  "path": "/api/patients/999"
}
```

---

### Update Patient
**PUT** `/patients/{patientId}`

Updates an existing patient record.

**Path Parameters:**
- `patientId` (Long) - The patient's unique identifier

**Request Body:**
```json
{
  "firstName": "string",
  "lastName": "string",
  "age": 0,
  "phoneNumber": "string",
  "email": "string",
  "medicalHistory": "string"
}
```

**Response:** `200 OK`
```json
{
  "message": "Patient updated successfully",
  "data": {
    "patientId": 1,
    "firstName": "Jane",
    "lastName": "Smith",
    "age": 32,
    "phoneNumber": "0987654321",
    "email": "jane@example.com",
    "medicalHistory": "Hypertension",
    "createdAt": "2024-03-19T10:30:00",
    "updatedAt": "2024-03-19T11:00:00"
  },
  "status": "success"
}
```

---

### Delete Patient
**DELETE** `/patients/{patientId}`

Deletes a patient record.

**Path Parameters:**
- `patientId` (Long) - The patient's unique identifier

**Response:** `200 OK`
```json
{
  "message": "Patient deleted successfully",
  "status": "success"
}
```

---

### Search Patient by Name
**GET** `/patients/search?name=John`

Searches for patients by first or last name.

**Query Parameters:**
- `name` (String) - Search term (required)

**Response:** `200 OK`
```json
{
  "data": [
    {
      "patientId": 1,
      "firstName": "John",
      "lastName": "Doe",
      "age": 30,
      "phoneNumber": "1234567890",
      "email": "john@example.com",
      "medicalHistory": "No allergies",
      "createdAt": "2024-03-19T10:30:00",
      "updatedAt": "2024-03-19T10:30:00"
    }
  ],
  "total": 1,
  "status": "success"
}
```

---

### Get Patient by Phone Number
**GET** `/patients/phone/{phoneNumber}`

Retrieves a patient by phone number.

**Path Parameters:**
- `phoneNumber` (String) - Patient's phone number

**Response:** `200 OK`

---

### Get Patient by Email
**GET** `/patients/email/{email}`

Retrieves a patient by email address.

**Path Parameters:**
- `email` (String) - Patient's email address

**Response:** `200 OK`

---

### Check if Phone Exists
**GET** `/patients/exists/phone/{phoneNumber}`

Checks if a phone number is registered.

**Response:** `200 OK`
```json
{
  "exists": true,
  "status": "success"
}
```

---

### Check if Email Exists
**GET** `/patients/exists/email/{email}`

Checks if an email is registered.

**Response:** `200 OK`
```json
{
  "exists": false,
  "status": "success"
}
```

---

## Doctor Management Endpoints

### Create Doctor
**POST** `/doctors`

Creates a new doctor record.

**Request Body:**
```json
{
  "firstName": "string",
  "lastName": "string",
  "specialty": "string",
  "department": "string",
  "phoneNumber": "string",
  "email": "string",
  "qualifications": "string"
}
```

**Response:** `201 Created`

**Validation Rules:**
- `firstName`, `lastName` - Required, non-empty
- `specialty` - Required, non-empty
- `department` - Required, non-empty
- `phoneNumber` - Required, 10-15 digits, unique
- `email` - Optional, valid format if provided, unique

---

### Get All Doctors
**GET** `/doctors`

Retrieves all doctors.

**Response:** `200 OK`
```json
{
  "data": [
    {
      "doctorId": 1,
      "firstName": "Dr. Ahmed",
      "lastName": "Hassan",
      "specialty": "Cardiology",
      "department": "Heart & Vascular",
      "phoneNumber": "1111111111",
      "email": "dr.ahmed@hospital.com",
      "qualifications": "MD, Cardiologist",
      "createdAt": "2024-03-19T10:30:00",
      "updatedAt": "2024-03-19T10:30:00"
    }
  ],
  "total": 6,
  "status": "success"
}
```

---

### Get Doctor by ID
**GET** `/doctors/{doctorId}`

Retrieves a specific doctor.

**Path Parameters:**
- `doctorId` (Long) - Doctor's unique identifier

**Response:** `200 OK`

---

### Update Doctor
**PUT** `/doctors/{doctorId}`

Updates doctor information.

**Path Parameters:**
- `doctorId` (Long) - Doctor's unique identifier

**Request Body:**
```json
{
  "firstName": "string",
  "lastName": "string",
  "specialty": "string",
  "department": "string",
  "phoneNumber": "string",
  "email": "string",
  "qualifications": "string"
}
```

**Response:** `200 OK`

---

### Delete Doctor
**DELETE** `/doctors/{doctorId}`

Deletes a doctor record.

**Path Parameters:**
- `doctorId` (Long) - Doctor's unique identifier

**Response:** `200 OK`

---

### Get Doctor by Specialty
**GET** `/doctors/specialty/{specialty}`

Retrieves all doctors with specific specialty.

**Path Parameters:**
- `specialty` (String) - Medical specialty (e.g., "Cardiology")

**Response:** `200 OK`
```json
{
  "data": [
    {
      "doctorId": 1,
      "firstName": "Dr. Ahmed",
      "lastName": "Hassan",
      "specialty": "Cardiology",
      "department": "Heart & Vascular",
      "phoneNumber": "1111111111",
      "email": "dr.ahmed@hospital.com",
      "qualifications": "MD, Cardiologist",
      "createdAt": "2024-03-19T10:30:00",
      "updatedAt": "2024-03-19T10:30:00"
    }
  ],
  "total": 1,
  "status": "success"
}
```

---

### Get Doctor by Department
**GET** `/doctors/department/{department}`

Retrieves all doctors in a specific department.

**Path Parameters:**
- `department` (String) - Department name

**Response:** `200 OK`

---

### Filter Doctors by Specialty and Department
**GET** `/doctors/filter?specialty=Cardiology&department=Heart%20%26%20Vascular`

Filters doctors by both specialty and department.

**Query Parameters:**
- `specialty` (String) - Medical specialty (required)
- `department` (String) - Department name (required)

**Response:** `200 OK`

---

### Search Doctor by Name
**GET** `/doctors/search?name=Ahmed`

Searches for doctors by name.

**Query Parameters:**
- `name` (String) - Search term (required)

**Response:** `200 OK`

---

### Get Total Doctors Count
**GET** `/doctors/count`

Gets the total number of doctors.

**Response:** `200 OK`
```json
{
  "total": 6,
  "status": "success"
}
```

---

## Disease Management Endpoints

### Create Disease
**POST** `/diseases`

Creates a new disease record.

**Request Body:**
```json
{
  "diseaseName": "string",
  "description": "string",
  "symptoms": "string",
  "treatment": "string"
}
```

**Response:** `201 Created`

**Validation Rules:**
- `diseaseName` - Required, non-empty, unique

---

### Get All Diseases
**GET** `/diseases`

Retrieves all diseases.

**Response:** `200 OK`

---

### Get Disease by ID
**GET** `/diseases/{diseaseId}`

Retrieves a specific disease.

**Path Parameters:**
- `diseaseId` (Long) - Disease's unique identifier

**Response:** `200 OK`

---

### Update Disease
**PUT** `/diseases/{diseaseId}`

Updates disease information.

**Path Parameters:**
- `diseaseId` (Long) - Disease's unique identifier

**Request Body:**
```json
{
  "diseaseName": "string",
  "description": "string",
  "symptoms": "string",
  "treatment": "string"
}
```

**Response:** `200 OK`

---

### Delete Disease
**DELETE** `/diseases/{diseaseId}`

Deletes a disease record.

**Path Parameters:**
- `diseaseId` (Long) - Disease's unique identifier

**Response:** `200 OK`

---

### Get Disease by Name
**GET** `/diseases/name/{diseaseName}`

Retrieves a disease by its name.

**Path Parameters:**
- `diseaseName` (String) - Disease name

**Response:** `200 OK`

---

### Search Disease by Name
**GET** `/diseases/search?name=Hypertension`

Searches for diseases by name.

**Query Parameters:**
- `name` (String) - Search term (required)

**Response:** `200 OK`

---

### Check if Disease Exists
**GET** `/diseases/exists/{diseaseName}`

Checks if a disease exists in the system.

**Path Parameters:**
- `diseaseName` (String) - Disease name

**Response:** `200 OK`
```json
{
  "exists": true,
  "status": "success"
}
```

---

## Appointment Management Endpoints

### Schedule Appointment
**POST** `/appointments`

Schedules a new appointment.

**Request Body:**
```json
{
  "patient": {
    "patientId": 1
  },
  "doctor": {
    "doctorId": 1
  },
  "disease": {
    "diseaseId": 1
  },
  "appointmentDateTime": "2024-03-25T14:30:00",
  "reason": "Regular checkup",
  "notes": "Additional notes"
}
```

**Response:** `201 Created`
```json
{
  "message": "Appointment scheduled successfully",
  "data": {
    "appointmentId": 1,
    "patient": {
      "patientId": 1,
      "firstName": "John",
      "lastName": "Doe"
    },
    "doctor": {
      "doctorId": 1,
      "firstName": "Dr. Ahmed",
      "lastName": "Hassan"
    },
    "disease": {
      "diseaseId": 1,
      "diseaseName": "Hypertension"
    },
    "appointmentDateTime": "2024-03-25T14:30:00",
    "reason": "Regular checkup",
    "status": "SCHEDULED",
    "notes": "Additional notes",
    "createdAt": "2024-03-19T10:30:00",
    "updatedAt": "2024-03-19T10:30:00"
  },
  "status": "success"
}
```

**Validation Rules:**
- `appointmentDateTime` - Required, must be in the future
- Doctor cannot have conflicting appointments (1-hour window)
- Patient cannot have conflicting appointments (2-hour window)

**Error on Conflict:** `409 Conflict`
```json
{
  "statusCode": 409,
  "message": "Doctor has conflicting appointment at this time",
  "timestamp": "2024-03-19T10:35:00",
  "path": "/api/appointments"
}
```

---

### Get All Appointments
**GET** `/appointments`

Retrieves all appointments.

**Response:** `200 OK`

---

### Get Appointment by ID
**GET** `/appointments/{appointmentId}`

Retrieves a specific appointment.

**Path Parameters:**
- `appointmentId` (Long) - Appointment's unique identifier

**Response:** `200 OK`

---

### Update Appointment
**PUT** `/appointments/{appointmentId}`

Updates appointment details.

**Path Parameters:**
- `appointmentId` (Long) - Appointment's unique identifier

**Request Body:**
```json
{
  "appointmentDateTime": "2024-03-25T15:00:00",
  "reason": "Follow-up checkup",
  "status": "SCHEDULED",
  "notes": "Patient called to reschedule"
}
```

**Response:** `200 OK`

---

### Delete Appointment
**DELETE** `/appointments/{appointmentId}`

Deletes an appointment record.

**Path Parameters:**
- `appointmentId` (Long) - Appointment's unique identifier

**Response:** `200 OK`

---

### Get Patient's Appointments
**GET** `/appointments/patient/{patientId}`

Retrieves all appointments for a specific patient.

**Path Parameters:**
- `patientId` (Long) - Patient's unique identifier

**Response:** `200 OK`

---

### Get Doctor's Appointments
**GET** `/appointments/doctor/{doctorId}`

Retrieves all appointments for a specific doctor.

**Path Parameters:**
- `doctorId` (Long) - Doctor's unique identifier

**Response:** `200 OK`

---

### Get Appointments by Disease
**GET** `/appointments/disease/{diseaseId}`

Retrieves all appointments for a specific disease.

**Path Parameters:**
- `diseaseId` (Long) - Disease's unique identifier

**Response:** `200 OK`

---

### Get Appointments by Status
**GET** `/appointments/status/{status}`

Retrieves appointments filtered by status.

**Path Parameters:**
- `status` (String) - Appointment status: SCHEDULED, COMPLETED, CANCELLED, RESCHEDULED

**Response:** `200 OK`
```json
{
  "data": [
    {
      "appointmentId": 1,
      "patient": {
        "patientId": 1,
        "firstName": "John",
        "lastName": "Doe"
      },
      "status": "SCHEDULED"
    }
  ],
  "total": 5,
  "status": "success"
}
```

---

### Get Appointments in Date Range
**GET** `/appointments/date-range?startDate=2024-03-20T00:00:00&endDate=2024-03-25T23:59:59`

Retrieves appointments between two dates.

**Query Parameters:**
- `startDate` (ISO 8601 DateTime) - Range start (required)
- `endDate` (ISO 8601 DateTime) - Range end (required)

**Response:** `200 OK`

---

### Get Patient's Appointments by Status
**GET** `/appointments/patient/{patientId}/status/{status}`

Retrieves specific patient's appointments filtered by status.

**Path Parameters:**
- `patientId` (Long) - Patient's unique identifier
- `status` (String) - Appointment status

**Response:** `200 OK`

---

### Get Doctor's Appointments by Status
**GET** `/appointments/doctor/{doctorId}/status/{status}`

Retrieves specific doctor's appointments filtered by status.

**Path Parameters:**
- `doctorId` (Long) - Doctor's unique identifier
- `status` (String) - Appointment status

**Response:** `200 OK`

---

### Get Doctor's Appointments in Date Range
**GET** `/appointments/doctor/{doctorId}/date-range?startDate=2024-03-20T00:00:00&endDate=2024-03-25T23:59:59`

Retrieves specific doctor's appointments between dates.

**Path Parameters:**
- `doctorId` (Long) - Doctor's unique identifier

**Query Parameters:**
- `startDate` (ISO 8601 DateTime) - Range start
- `endDate` (ISO 8601 DateTime) - Range end

**Response:** `200 OK`

---

### Cancel Appointment
**PUT** `/appointments/{appointmentId}/cancel`

Cancels an appointment.

**Path Parameters:**
- `appointmentId` (Long) - Appointment's unique identifier

**Response:** `200 OK`
```json
{
  "message": "Appointment cancelled successfully",
  "status": "success"
}
```

---

### Complete Appointment
**PUT** `/appointments/{appointmentId}/complete`

Marks an appointment as completed.

**Path Parameters:**
- `appointmentId` (Long) - Appointment's unique identifier

**Response:** `200 OK`

---

### Reschedule Appointment
**PUT** `/appointments/{appointmentId}/reschedule?newDateTime=2024-03-26T10:00:00`

Reschedules an appointment to a new date/time.

**Path Parameters:**
- `appointmentId` (Long) - Appointment's unique identifier

**Query Parameters:**
- `newDateTime` (ISO 8601 DateTime) - New appointment date/time

**Response:** `200 OK`
```json
{
  "message": "Appointment rescheduled successfully",
  "status": "success"
}
```

---

### Get Appointment Statistics
**GET** `/appointments/stats`

Retrieves overall appointment statistics.

**Response:** `200 OK`
```json
{
  "data": {
    "total": 25,
    "scheduled": 15,
    "completed": 8,
    "cancelled": 2
  },
  "status": "success"
}
```

---

### Get Scheduled Count
**GET** `/appointments/stats/scheduled`

Gets count of scheduled appointments.

**Response:** `200 OK`
```json
{
  "scheduled": 15,
  "status": "success"
}
```

---

### Get Completed Count
**GET** `/appointments/stats/completed`

Gets count of completed appointments.

**Response:** `200 OK`

---

### Get Cancelled Count
**GET** `/appointments/stats/cancelled`

Gets count of cancelled appointments.

**Response:** `200 OK`

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 400 | Bad Request - Invalid input |
| 404 | Not Found - Resource not found |
| 409 | Conflict - Scheduling conflict detected |
| 500 | Internal Server Error - Server error |

---

## Error Codes and Messages

| Scenario | Code | Message |
|----------|------|---------|
| Patient not found | 404 | Patient not found with ID: {id} |
| Doctor not found | 404 | Doctor not found with ID: {id} |
| Disease not found | 404 | Disease not found with ID: {id} |
| Appointment not found | 404 | Appointment not found with ID: {id} |
| Doctor conflict | 409 | Doctor has conflicting appointment at this time |
| Patient conflict | 409 | Patient has conflicting appointment at this time |
| Invalid input | 400 | Validation error messages |
| Duplicate disease | 400 | Disease already exists: {name} |

---

## Rate Limiting

Currently, no rate limiting is implemented. For production environments, implement rate limiting to prevent abuse.

---

## CORS

The API supports CORS from all origins. For production, restrict origins to your specific domains.

---

## Pagination

Pagination is not currently implemented but can be added to endpoints returning lists.

---

## Versioning

The current API version is v1 (no version prefix in URLs). Future versions can be added as `/api/v2/...`

---

## Testing with Examples

### Create a Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Johnson",
    "age": 28,
    "phoneNumber": "5556667777",
    "email": "alice.j@example.com",
    "medicalHistory": "Seasonal allergies"
  }'
```

### Schedule an Appointment
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "patient": {"patientId": 1},
    "doctor": {"doctorId": 1},
    "disease": {"diseaseId": 1},
    "appointmentDateTime": "2024-03-25T14:30:00",
    "reason": "Hypertension management"
  }'
```

### Get All Scheduled Appointments
```bash
curl http://localhost:8080/api/appointments/status/SCHEDULED
```

### Reschedule an Appointment
```bash
curl -X PUT "http://localhost:8080/api/appointments/1/reschedule?newDateTime=2024-03-26T10:00:00"
```

---

For more information, refer to the README.md and SETUP.md files.
