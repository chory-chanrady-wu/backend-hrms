# HRMS Backend (Spring Boot) By CHORY Chanrady

## Overview
This project is a Human Resource Management System (HRMS) backend built with Spring Boot. It provides RESTful APIs for managing employee data, attendance, payroll, leave requests, job postings, and more. The backend is designed for extensibility, security, and ease of integration with frontend or third-party systems.

## Features
- Employee management
- Attendance tracking
- Payroll processing
- Leave request and approval
- Job postings and candidate management
- Role-based authentication and authorization
- Audit logging

## Technology Stack
- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- Maven
- Docker (optional)
- H2/MySQL/PostgreSQL (configurable)

## Project Structure
- `src/main/java/com/chanrady/hrms/` — Main application code
- `src/main/resources/` — Configuration files
- `src/test/java/com/chanrady/hrms/` — Test cases
- `pom.xml` — Maven build configuration
- `Dockerfile`, `docker-compose.yaml` — Containerization support

## Setup Instructions
### Prerequisites
- Java JDK 21 or higher
- Maven (or use the included `mvnw.cmd` wrapper)
- Git
- (Optional) Docker & Docker Compose

### Clone the Repository
```
git clone <your-repo-url>
cd backend
```

### Configure the Application
Edit `src/main/resources/application.properties` or `application-dev.yaml` to set up your database and environment variables as needed.

### Build the Project
```
./mvnw.cmd clean install
```

### Run the Application
```
./mvnw.cmd spring-boot:run
```
The backend will start on [http://localhost:7777](http://localhost:7777).

## API Usage Overview
### Attendance Management Endpoints
- **Create Attendance**
  - `POST /api/v1/attendance`
  - Request body: `AttendanceDTO`
- **Get Attendance by ID**
  - `GET /api/v1/attendance/{id}`
- **Get All Attendance**
  - `GET /api/v1/attendance`
- **Get Attendance by Employee**
  - `GET /api/v1/attendance/employee/{employeeId}`
- **Update Attendance**
  - `PUT /api/v1/attendance/{id}`
- **Delete Attendance**
  - `DELETE /api/v1/attendance/{id}`

> For other modules (employee, payroll, leave, etc.), similar RESTful endpoints are available under `/api/v1/`.

## Running Tests
```
./mvnw.cmd test
```
Test results will be available in the `target/surefire-reports/` directory.

## Docker Usage
### Build Docker Image
```
docker build -t hrms-backend .
```
### Run with Docker Compose
```
docker-compose up --build
```
This will start the backend and any dependent services as defined in `docker-compose.yaml`.

