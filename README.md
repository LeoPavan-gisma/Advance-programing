# Patient Appointment Management System (PAMS)

## Overview
A comprehensive REST API-based Patient Appointment Management System built with Spring Boot, designed to manage patients, doctors, diseases, and medical appointments efficiently.

## Features

### Patient Management
- Create, retrieve, update, and delete patient records
- Search patients by name, phone number, or email
- View patient medical history
- Track patient appointments

### Doctor Management
- Manage doctor profiles with specialty and department information
- Search doctors by specialty or department
- Filter doctors by multiple criteria
- Track doctor availability through appointments

### Disease Management
- Maintain a comprehensive disease database
- Store disease descriptions, symptoms, and treatment information
- Search diseases by name
- Link diseases to patient appointments

### Appointment Management
- Schedule medical appointments between patients and doctors
- **Conflict Detection**: Automatic prevention of double-booking for both doctors and patients
- **Status Management**: Track appointment status (Scheduled, Completed, Cancelled, Rescheduled)
- **Flexible Scheduling**: View appointments by patient, doctor, disease, or date range
- **Appointment Operations**: Cancel, complete, or reschedule appointments
- **Statistics**: Monitor appointment metrics (total, scheduled, completed, cancelled)

## Technology Stack

- **Framework**: Spring Boot 3.1.0
- **Language**: Java 17
- **Database**: H2 (In-Memory, easily replaceable with MySQL/PostgreSQL)
- **ORM**: Hibernate JPA
- **Build Tool**: Maven
- **Validation**: Jakarta Validation API
- **Architecture**: Manual getters/setters (Lombok removed for Java 24 compatibility)

## Project Structure

```
PatientAppointmentSystem/
├── src/main/java/com/gisma/pams/
│   ├── controller/          # REST API endpoints
│   │   ├── PatientController
│   │   ├── DoctorController
│   │   ├── DiseaseController
│   │   └── AppointmentController
│   ├── service/             # Business logic
│   │   ├── PatientService
│   │   ├── DoctorService
│   │   ├── DiseaseService
│   │   └── AppointmentService
│   ├── repository/          # Database access layer
│   │   ├── PatientRepository
│   │   ├── DoctorRepository
│   │   ├── DiseaseRepository
│   │   └── AppointmentRepository
│   ├── model/               # Entity classes
│   │   ├── Patient
│   │   ├── Doctor
│   │   ├── Disease
│   │   └── Appointment
│   ├── exception/           # Exception handling
│   │   ├── GlobalExceptionHandler
│   │   ├── PatientNotFoundException
│   │   ├── DoctorNotFoundException
│   │   ├── DiseaseNotFoundException
│   │   ├── AppointmentNotFoundException
│   │   └── AppointmentConflictException
│   ├── DataInitializationConfig  # Sample data initialization
│   └── PatientAppointmentApplication  # Application entry point
├── src/main/resources/
│   └── application.properties   # Configuration
├── pom.xml                  # Maven dependencies
└── README.md               # Documentation
```

## Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Steps

1. **Clone or Download the project** to your local machine

2. **Navigate to the project directory**
```bash
cd PatientAppointmentSystem
```

3. **Build the project**
```bash
mvn clean install
```

4. **Run the application**
```bash
mvn spring-boot:run
```

5. **Access the application**
   - Base URL: `http://localhost:8080/api`
   - H2 Console: `http://localhost:8080/api/h2-console`

## API Endpoints

### Patient Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/patients` | Create new patient |
| GET | `/patients` | Get all patients |
| GET | `/patients/{patientId}` | Get patient by ID |
| PUT | `/patients/{patientId}` | Update patient |
| DELETE | `/patients/{patientId}` | Delete patient |
| GET | `/patients/search?name=` | Search patient by name |
| GET | `/patients/phone/{phoneNumber}` | Get patient by phone |
| GET | `/patients/email/{email}` | Get patient by email |

### Doctor Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/doctors` | Create new doctor |
| GET | `/doctors` | Get all doctors |
| GET | `/doctors/{doctorId}` | Get doctor by ID |
| PUT | `/doctors/{doctorId}` | Update doctor |
| DELETE | `/doctors/{doctorId}` | Delete doctor |
| GET | `/doctors/specialty/{specialty}` | Get doctors by specialty |
| GET | `/doctors/department/{department}` | Get doctors by department |
| GET | `/doctors/filter?specialty=&department=` | Get doctors by specialty and department |

### Disease Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/diseases` | Create new disease |
| GET | `/diseases` | Get all diseases |
| GET | `/diseases/{diseaseId}` | Get disease by ID |
| PUT | `/diseases/{diseaseId}` | Update disease |
| DELETE | `/diseases/{diseaseId}` | Delete disease |
| GET | `/diseases/search?name=` | Search disease by name |
| GET | `/diseases/name/{diseaseName}` | Get disease by name |

### Appointment Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/appointments` | Schedule appointment |
| GET | `/appointments` | Get all appointments |
| GET | `/appointments/{appointmentId}` | Get appointment by ID |
| PUT | `/appointments/{appointmentId}` | Update appointment |
| DELETE | `/appointments/{appointmentId}` | Delete appointment |
| GET | `/appointments/patient/{patientId}` | Get appointments for patient |
| GET | `/appointments/doctor/{doctorId}` | Get appointments for doctor |
| GET | `/appointments/status/{status}` | Get appointments by status |
| GET | `/appointments/date-range?startDate=&endDate=` | Get appointments in date range |
| PUT | `/appointments/{appointmentId}/cancel` | Cancel appointment |
| PUT | `/appointments/{appointmentId}/complete` | Mark as completed |
| PUT | `/appointments/{appointmentId}/reschedule?newDateTime=` | Reschedule appointment |
| GET | `/appointments/stats` | Get appointment statistics |

## Sample Data

The application initializes with sample data for testing:

**Patients**: 5 patients with various medical histories
**Doctors**: 6 doctors from different specialties (Cardiology, Pediatrics, Orthopedics, Dermatology, Neurology, Oncology)
**Diseases**: 8 common diseases with descriptions, symptoms, and treatments

## Key Business Logic

### Conflict Detection
- **Doctor Scheduling**: Prevents overbooking within 1-hour windows
- **Patient Scheduling**: Prevents multiple appointments within 2-hour windows

### Validation
- Email format validation
- Phone number format validation (10-15 digits)
- Age validation for patients
- Future date validation for appointments
- Duplicate disease name prevention

### Transaction Management
- All service operations are transactional for data consistency
- Rollback on validation failures

## Error Handling

The application provides comprehensive error responses with:
- HTTP status codes (200, 201, 400, 404, 409, 500)
- Error messages describing the issue
- Timestamp of the error
- Request path that caused the error

## Example Requests

### Create Patient
```json
POST /api/patients
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 30,
  "phoneNumber": "1234567890",
  "email": "john.doe@email.com",
  "medicalHistory": "No known allergies"
}
```

### Schedule Appointment
```json
POST /api/appointments
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
  "reason": "Regular checkup"
}
```

## Database Configuration

The application uses H2 in-memory database by default. To switch to MySQL:

1. Add MySQL dependency in `pom.xml`
2. Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pams_db
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## Performance Considerations

- Indexed queries on frequently searched fields (phone, email)
- Efficient relationship loading using EAGER fetch strategy
- Transactional operations to reduce database round trips

## Security Notes

For production deployment:
- Implement authentication and authorization
- Disable H2 console
- Validate and sanitize all inputs
- Use HTTPS
- Implement rate limiting
- Add CORS restrictions

## Testing

The application includes sample data for manual testing. Spring Boot features can be leveraged for automated testing of controllers and services.

## Future Enhancements

- Appointment reminders and notifications
- Payment management
- Medical records storage
- Prescription management
- Telemedicine integration
- Mobile application
- Dashboard analytics
- Prescription tracking
- Medical document upload


## License

This project is created for educational purposes (M604 Advanced Programming Module).
