# Setup Guide - Patient Appointment Management System

## Prerequisites

Before starting, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 17 or higher
  - Download from: https://www.oracle.com/java/technologies/javase-jdk17-downloads.html
  - Or use OpenJDK from https://adoptium.net/

- **Apache Maven**: Version 3.6 or higher
  - Download from: https://maven.apache.org/download.cgi
  - Installation guide: https://maven.apache.org/install.html

- **Git** (optional, for version control)
  - Download from: https://git-scm.com/

## Environment Setup

### Step 1: Verify Java Installation

Open Command Prompt/Terminal and run:
```bash
java -version
javac -version
```

You should see output showing Java 17+

### Step 2: Verify Maven Installation

```bash
mvn -version
```

### Step 3: Download/Clone the Project

Navigate to your desired directory and clone or extract the project:

```bash
cd C:\Users\Leore\Desktop\advsance programing
```

## Build and Run

### Step 1: Build the Project

```bash
cd PatientAppointmentSystem
mvn clean install
```

This command:
- Cleans previous build artifacts
- Downloads dependencies
- Compiles the source code
- Runs tests (if any)
- Creates the JAR file

### Step 2: Run the Application

**Option 1: Using Maven**
```bash
mvn spring-boot:run
```

**Option 2: Using Java (after build)**
```bash
java -jar target/patient-appointment-system-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Database Configuration

### Default Configuration (H2 In-Memory)

By default, the application uses H2 in-memory database. No additional setup required.

### Switch to MySQL

If you want to use MySQL instead:

1. **Install MySQL** (if not already installed)
   - Download from: https://www.mysql.com/downloads/

2. **Create a database**
```sql
CREATE DATABASE pams_db;
```

3. **Update Dependencies in pom.xml**

Replace the H2 dependency with:
```xml
<!-- MySQL Driver -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
    <scope>runtime</scope>
</dependency>
```

4. **Update application.properties**

Open `src/main/resources/application.properties` and replace database section:

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/pams_db
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

5. **Rebuild and run**
```bash
mvn clean install
mvn spring-boot:run
```

### Switch to PostgreSQL

1. **Install PostgreSQL**
   - Download from: https://www.postgresql.org/download/

2. **Create database**
```sql
CREATE DATABASE pams_db;
```

3. **Update pom.xml**
```xml
<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.5.4</version>
    <scope>runtime</scope>
</dependency>
```

4. **Update application.properties**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pams_db
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Accessing the Application

### REST API
- **Base URL**: http://localhost:8080/api
- Test using Postman, Insomnia, or curl

### H2 Database Console (if using H2)
- **URL**: http://localhost:8080/api/h2-console
- **JDBC URL**: jdbc:h2:mem:pamsdb
- **Username**: sa
- **Password**: (leave blank)

## Testing the APIs

### Using Postman

1. **Download Postman** from https://www.postman.com/downloads/

2. **Create requests** using the endpoints listed in README.md

3. **Example - Create Patient**:
   - Method: POST
   - URL: http://localhost:8080/api/patients
   - Body (JSON):
   ```json
   {
     "firstName": "John",
     "lastName": "Doe",
     "age": 30,
     "phoneNumber": "1234567890",
     "email": "john@example.com",
     "medicalHistory": "No known allergies"
   }
   ```

### Using cURL

```bash
# Create patient
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "age": 30,
    "phoneNumber": "1234567890",
    "email": "john@example.com",
    "medicalHistory": "No known allergies"
  }'

# Get all patients
curl http://localhost:8080/api/patients

# Get patient by ID
curl http://localhost:8080/api/patients/1
```

## Troubleshooting

### Issue: "java: no matching method for name run()"

**Solution**: Update your IDE and Maven plugins
```bash
mvn clean install -U
```

### Issue: Port 8080 already in use

Change the port in `application.properties`:
```properties
server.port=8081
```

### Issue: Database connection error

Verify:
- MySQL/PostgreSQL service is running
- Database credentials are correct
- Database URL is accessible

### Issue: Maven build fails with "Cannot find symbol"

```bash
# Clear Maven cache
mvn clean install -U
```

## IDE Setup

### IntelliJ IDEA

1. Open project: File → Open → select project folder
2. Maven should auto-detect pom.xml
3. Let IDE download dependencies
4. Run: Right-click `PatientAppointmentApplication.java` → Run

### Visual Studio Code

1. Install extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack

2. Open project folder
3. Let VS Code set up the Java environment
4. Run: Press Ctrl+F5 or use the Run menu

## Sample API Responses

### Successful Patient Creation
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
    "medicalHistory": "No known allergies",
    "createdAt": "2024-03-19T10:30:00",
    "updatedAt": "2024-03-19T10:30:00"
  },
  "status": "success"
}
```

### Error Response
```json
{
  "statusCode": 404,
  "message": "Patient not found with ID: 999",
  "timestamp": "2024-03-19T10:35:00",
  "path": "/api/patients/999"
}
```

## Performance Tips

1. **Use Indexes**: The repository layer includes indexed queries for efficient searches
2. **Batch Operations**: Use bulk insert for large dataset imports
3. **Pagination**: Can be added for endpoints returning large datasets
4. **Caching**: Consider adding Spring Cache for frequently accessed data

## Next Steps

After setup:

1. Create sample data through APIs
2. Test all endpoints with Postman
3. Review the code structure and business logic
4. Extend functionality as needed for your requirements
5. Deploy to production environment

## Common Questions

**Q: How do I reset the database?**
A: With H2, data is cleared on restart. With MySQL/PostgreSQL, drop and recreate the database.

**Q: Can I use a different port?**
A: Yes, change `server.port` in `application.properties`

**Q: How do I enable query logging?**
A: Already enabled - check console output. For file logging, add logging configuration to `application.properties`

**Q: Can I add more sample data?**
A: Modify `DataInitializationConfig.java` and rebuild

## Resources

- Spring Boot Documentation: https://spring.io/projects/spring-boot
- JPA/Hibernate Guide: https://hibernate.org/orm/
- Maven Getting Started: https://maven.apache.org/guides/getting-started/
- H2 Database: https://h2database.com/
- MySQL Documentation: https://dev.mysql.com/doc/

## Support

If you encounter issues:

1. Check the error message and logs carefully
2. Verify your environment setup
3. Ensure all dependencies are installed
4. Check if ports are available
5. Review stack traces for detailed information
