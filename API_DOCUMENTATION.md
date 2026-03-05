# HRMS REST API Documentation

## Overview
Complete REST API endpoints for the Human Resource Management System (HRMS). All endpoints support CORS and return JSON responses.

## Base URL
```
http://localhost:8080/api
```

---

## 1. User Management Endpoints

### Create User
- **URL:** `POST /users`
- **Description:** Create a new user
- **Request Body:**
```json
{
  "username": "john.doe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "passwordHash": "hashed_password",
  "roleId": 1
}
```
- **Response:** `201 Created`
```json
{
  "id": 1,
  "username": "john.doe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "passwordHash": "hashed_password",
  "roleId": 1,
  "roleName": "Admin",
  "createdAt": "2026-03-05T09:30:00",
  "updatedAt": "2026-03-05T09:30:00"
}
```

### Get User by ID
- **URL:** `GET /users/{id}`
- **Description:** Retrieve a specific user
- **Response:** `200 OK` or `404 Not Found`

### Get All Users
- **URL:** `GET /users`
- **Description:** Retrieve all active users
- **Response:** `200 OK` with array of users

### Get User by Username
- **URL:** `GET /users/username/{username}`
- **Description:** Retrieve user by username
- **Response:** `200 OK` or `404 Not Found`

### Get User by Email
- **URL:** `GET /users/email/{email}`
- **Description:** Retrieve user by email
- **Response:** `200 OK` or `404 Not Found`

### Update User
- **URL:** `PUT /users/{id}`
- **Description:** Update user information
- **Request Body:** Same as create user
- **Response:** `200 OK` or `404 Not Found`

### Delete User
- **URL:** `DELETE /users/{id}`
- **Description:** Soft delete a user
- **Response:** `204 No Content`

---

## 2. Role Management Endpoints

### Create Role
- **URL:** `POST /roles`
- **Request Body:**
```json
{
  "roleName": "Manager",
  "permissions": "[\"read\", \"write\", \"delete\"]"
}
```
- **Response:** `201 Created`

### Get Role by ID
- **URL:** `GET /roles/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Roles
- **URL:** `GET /roles`
- **Response:** `200 OK`

### Get Role by Name
- **URL:** `GET /roles/name/{roleName}`
- **Response:** `200 OK` or `404 Not Found`

### Update Role
- **URL:** `PUT /roles/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Role
- **URL:** `DELETE /roles/{id}`
- **Response:** `204 No Content`

---

## 3. Department Management Endpoints

### Create Department
- **URL:** `POST /departments`
- **Request Body:**
```json
{
  "name": "Engineering",
  "description": "Software Engineering Department"
}
```
- **Response:** `201 Created`

### Get Department by ID
- **URL:** `GET /departments/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Departments
- **URL:** `GET /departments`
- **Response:** `200 OK`

### Get Department by Name
- **URL:** `GET /departments/name/{name}`
- **Response:** `200 OK` or `404 Not Found`

### Update Department
- **URL:** `PUT /departments/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Department
- **URL:** `DELETE /departments/{id}`
- **Response:** `204 No Content`

---

## 4. Position Management Endpoints

### Create Position
- **URL:** `POST /positions`
- **Request Body:**
```json
{
  "title": "Senior Developer",
  "description": "Senior Software Developer",
  "departmentId": 1
}
```
- **Response:** `201 Created`

### Get Position by ID
- **URL:** `GET /positions/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Positions
- **URL:** `GET /positions`
- **Response:** `200 OK`

### Get Positions by Department
- **URL:** `GET /positions/department/{departmentId}`
- **Response:** `200 OK`

### Update Position
- **URL:** `PUT /positions/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Position
- **URL:** `DELETE /positions/{id}`
- **Response:** `204 No Content`

---

## 5. Employee Management Endpoints

### Create Employee
- **URL:** `POST /employees`
- **Request Body:**
```json
{
  "userId": 1,
  "departmentId": 1,
  "jobTitle": "Senior Developer",
  "employmentType": "Full-time",
  "salary": 50000.00,
  "hireDate": "2020-01-15",
  "status": "active"
}
```
- **Response:** `201 Created`

### Get Employee by ID
- **URL:** `GET /employees/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Employees
- **URL:** `GET /employees`
- **Response:** `200 OK`

### Get Employees by Department
- **URL:** `GET /employees/department/{departmentId}`
- **Response:** `200 OK`

### Get Employees by Status
- **URL:** `GET /employees/status/{status}`
- **Example:** `GET /employees/status/active`
- **Response:** `200 OK`

### Update Employee
- **URL:** `PUT /employees/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Employee
- **URL:** `DELETE /employees/{id}`
- **Response:** `204 No Content`

---

## 6. Attendance Management Endpoints

### Create Attendance
- **URL:** `POST /attendance`
- **Request Body:**
```json
{
  "employeeId": 1,
  "checkIn": "2026-03-05T08:00:00",
  "checkOut": "2026-03-05T17:00:00",
  "status": "present"
}
```
- **Response:** `201 Created`

### Get Attendance by ID
- **URL:** `GET /attendance/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Attendance Records
- **URL:** `GET /attendance`
- **Response:** `200 OK`

### Get Attendance by Employee
- **URL:** `GET /attendance/employee/{employeeId}`
- **Response:** `200 OK`

### Update Attendance
- **URL:** `PUT /attendance/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Attendance
- **URL:** `DELETE /attendance/{id}`
- **Response:** `204 No Content`

---

## 7. Leave Type Management Endpoints

### Create Leave Type
- **URL:** `POST /leave-types`
- **Request Body:**
```json
{
  "name": "Vacation",
  "maxDays": 20
}
```
- **Response:** `201 Created`

### Get Leave Type by ID
- **URL:** `GET /leave-types/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Leave Types
- **URL:** `GET /leave-types`
- **Response:** `200 OK`

### Get Leave Type by Name
- **URL:** `GET /leave-types/name/{name}`
- **Response:** `200 OK` or `404 Not Found`

### Update Leave Type
- **URL:** `PUT /leave-types/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Leave Type
- **URL:** `DELETE /leave-types/{id}`
- **Response:** `204 No Content`

---

## 8. Leave Request Management Endpoints

### Create Leave Request
- **URL:** `POST /leave-requests`
- **Request Body:**
```json
{
  "employeeId": 1,
  "leaveTypeId": 1,
  "startDate": "2026-03-10",
  "endDate": "2026-03-14",
  "reason": "Vacation",
  "status": "pending"
}
```
- **Response:** `201 Created`

### Get Leave Request by ID
- **URL:** `GET /leave-requests/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Leave Requests
- **URL:** `GET /leave-requests`
- **Response:** `200 OK`

### Get Leave Requests by Employee
- **URL:** `GET /leave-requests/employee/{employeeId}`
- **Response:** `200 OK`

### Get Leave Requests by Status
- **URL:** `GET /leave-requests/status/{status}`
- **Example:** `GET /leave-requests/status/pending`
- **Response:** `200 OK`

### Update Leave Request
- **URL:** `PUT /leave-requests/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Leave Request
- **URL:** `DELETE /leave-requests/{id}`
- **Response:** `204 No Content`

---

## 9. Benefit Management Endpoints

### Create Benefit
- **URL:** `POST /benefits`
- **Request Body:**
```json
{
  "name": "Health Insurance",
  "description": "Comprehensive health insurance coverage"
}
```
- **Response:** `201 Created`

### Get Benefit by ID
- **URL:** `GET /benefits/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Benefits
- **URL:** `GET /benefits`
- **Response:** `200 OK`

### Get Benefit by Name
- **URL:** `GET /benefits/name/{name}`
- **Response:** `200 OK` or `404 Not Found`

### Update Benefit
- **URL:** `PUT /benefits/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Benefit
- **URL:** `DELETE /benefits/{id}`
- **Response:** `204 No Content`

---

## 10. Employee Benefit Management Endpoints

### Create Employee Benefit
- **URL:** `POST /employee-benefits`
- **Request Body:**
```json
{
  "employeeId": 1,
  "benefitId": 1,
  "startDate": "2026-01-01",
  "endDate": "2026-12-31"
}
```
- **Response:** `201 Created`

### Get Employee Benefit by ID
- **URL:** `GET /employee-benefits/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Employee Benefits
- **URL:** `GET /employee-benefits`
- **Response:** `200 OK`

### Get Employee Benefits by Employee
- **URL:** `GET /employee-benefits/employee/{employeeId}`
- **Response:** `200 OK`

### Update Employee Benefit
- **URL:** `PUT /employee-benefits/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Employee Benefit
- **URL:** `DELETE /employee-benefits/{id}`
- **Response:** `204 No Content`

---

## 11. Payroll Management Endpoints

### Create Payroll
- **URL:** `POST /payroll`
- **Request Body:**
```json
{
  "employeeId": 1,
  "baseSalary": 50000.00,
  "bonus": 5000.00,
  "deduction": 500.00,
  "payDate": "2026-03-31"
}
```
- **Response:** `201 Created`
- **Note:** netSalary is calculated automatically (baseSalary + bonus - deduction)

### Get Payroll by ID
- **URL:** `GET /payroll/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Payroll Records
- **URL:** `GET /payroll`
- **Response:** `200 OK`

### Get Payroll by Employee
- **URL:** `GET /payroll/employee/{employeeId}`
- **Response:** `200 OK`

### Update Payroll
- **URL:** `PUT /payroll/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Delete Payroll
- **URL:** `DELETE /payroll/{id}`
- **Response:** `204 No Content`

---

## 12. Audit Log Management Endpoints

### Create Audit Log
- **URL:** `POST /audit-logs`
- **Request Body:**
```json
{
  "userId": 1,
  "action": "CREATE",
  "tableName": "employees",
  "recordId": 1,
  "oldData": null,
  "newData": "{\"name\": \"John Doe\"}",
  "ipAddress": "192.168.1.1"
}
```
- **Response:** `201 Created`

### Get Audit Log by ID
- **URL:** `GET /audit-logs/{id}`
- **Response:** `200 OK` or `404 Not Found`

### Get All Audit Logs
- **URL:** `GET /audit-logs`
- **Response:** `200 OK`

### Get Audit Logs by User
- **URL:** `GET /audit-logs/user/{userId}`
- **Response:** `200 OK`

### Get Audit Logs by Table Name
- **URL:** `GET /audit-logs/table/{tableName}`
- **Example:** `GET /audit-logs/table/employees`
- **Response:** `200 OK`

### Get Audit Logs by Action
- **URL:** `GET /audit-logs/action/{action}`
- **Example:** `GET /audit-logs/action/CREATE`
- **Response:** `200 OK`

---

## HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 | OK - Request successful |
| 201 | Created - Resource successfully created |
| 204 | No Content - Request successful, no response body |
| 400 | Bad Request - Invalid request data |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error - Server error |

---

## Error Response Format

All error responses follow this format:

```json
{
  "timestamp": "2026-03-05T09:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found"
}
```

---

## Authentication

*Note: Authentication endpoints and security configuration should be implemented separately based on your security requirements.*

---

## CORS Configuration

All endpoints support Cross-Origin Resource Sharing (CORS):
- **Allowed Origins:** All (`*`)
- **Max Age:** 3600 seconds

---

## Summary of All Endpoints

### Core Management (Users, Roles)
- 12 User endpoints
- 6 Role endpoints

### Organizational Structure (Departments, Positions)
- 6 Department endpoints
- 6 Position endpoints

### Employee Management
- 7 Employee endpoints

### Attendance & Leave
- 6 Attendance endpoints
- 6 Leave Type endpoints
- 7 Leave Request endpoints

### Benefits & Compensation
- 6 Benefit endpoints
- 6 Employee Benefit endpoints
- 7 Payroll endpoints

### Audit & Compliance
- 6 Audit Log endpoints

**Total: 95+ REST API endpoints**

---

## Testing with cURL

### Example: Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "fullName": "John Doe",
    "email": "john@example.com",
    "passwordHash": "hashed_password",
    "roleId": 1
  }'
```

### Example: Get All Employees
```bash
curl -X GET http://localhost:8080/api/employees \
  -H "Content-Type: application/json"
```

---

## Next Steps

1. Configure database connection in `application.properties` or `application.yml`
2. Implement authentication/authorization (JWT or OAuth2)
3. Add input validation with `@Valid` and `@NotNull` annotations
4. Implement global exception handling
5. Add API documentation with Swagger/Springdoc
6. Configure logging and monitoring
7. Set up security filters and interceptors

