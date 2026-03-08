# Login Response with Employee Details

## Overview
The login endpoint has been updated to include full employee information in the response alongside JWT tokens. This allows frontend to display complete user and employee details immediately after login.

## Changes Made

### 1. EmployeeRepository
**File:** `src/main/java/com/chanrady/hrms/repository/EmployeeRepository.java`

Added method to find employee by user ID:
```java
Optional<Employee> findByUserId(Integer userId);
```

### 2. EmployeeService Interface
**File:** `src/main/java/com/chanrady/hrms/service/EmployeeService.java`

Added method signature:
```java
Optional<EmployeeDTO> getEmployeeByUserId(Integer userId);
```

### 3. EmployeeServiceImpl
**File:** `src/main/java/com/chanrady/hrms/service/EmployeeServiceImpl.java`

Implemented the method:
```java
@Override
public Optional<EmployeeDTO> getEmployeeByUserId(Integer userId) {
    return employeeRepository.findByUserId(userId).map(this::convertToDTO);
}
```

### 4. AuthController
**File:** `src/main/java/com/chanrady/hrms/controller/AuthController.java`

Updated login endpoint to:
1. Inject EmployeeService
2. Fetch employee details by user ID after authentication
3. Include all employee fields in the response user object

## Login Response Format

### Endpoint
```
POST /api/v1/auth/login
```

### Request Body
```json
{
  "username": "seed.admin",
  "password": "seed_hash_1"
}
```

Or use email instead of username:
```json
{
  "email": "seed.admin@hrms.local",
  "password": "seed_hash_1"
}
```

### Successful Response (200 OK)
```json
{
  "success": true,
  "message": "Login successful",
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 11,
    "username": "seed.admin",
    "fullName": "Seed Admin",
    "email": "seed.admin@hrms.local",
    "roleId": 11,
    "roleName": "ADMIN",
    "employeeId": 11,
    "userId": 11,
    "departmentId": 11,
    "departmentName": "Engineering",
    "positionId": 11,
    "positionName": "Senior Developer",
    "employmentType": "Full-time",
    "salary": 1800.00,
    "hireDate": "2022-01-10",
    "status": "active",
    "imageUrl": "https://res.cloudinary.com/demo/image/upload/sample.jpg",
    "createdAt": "2026-03-05T22:37:08.927211",
    "updatedAt": "2026-03-05T22:37:08.942283"
  }
}
```

### Error Response (401 Unauthorized)
```json
{
  "success": false,
  "message": "Invalid username/email or password",
  "status": 400
}
```

## User Object Fields

### User Account Fields (always present)
- `id` - User ID
- `username` - Username
- `fullName` - Full name
- `email` - Email address
- `roleId` - Role ID (1=ADMIN, 2=MANAGER, 3=HR, 4=EMPLOYEE)
- `roleName` - Role name
- `createdAt` - Account creation timestamp
- `updatedAt` - Last update timestamp

### Employee Fields (present if user has employee record)
- `employeeId` - Employee record ID
- `userId` - User ID (reference back to user)
- `departmentId` - Department ID
- `departmentName` - Department name
- `positionId` - Position ID
- `positionName` - Position title
- `employmentType` - Employment type (Full-time, Part-time, Contract, etc.)
- `salary` - Base salary
- `hireDate` - Hire date
- `status` - Employment status (active, inactive, on_leave, etc.)
- `imageUrl` - Profile image URL from Cloudinary

## Notes

1. **Employee Details Optional**: If a user doesn't have an employee record, employee fields will not be present in the response
2. **Password Not Included**: The response never includes `passwordHash` for security
3. **Token Usage**: Frontend should use the `accessToken` in the Authorization header: `Authorization: Bearer <accessToken>`
4. **Token Refresh**: The `refreshToken` can be used to obtain new access tokens
5. **Token Expiration**: The `expiresIn` value is in seconds (default 3600 = 1 hour)

## Frontend Usage Example

```javascript
// Login request
const response = await fetch('/api/v1/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    username: 'seed.admin',
    password: 'seed_hash_1'
  })
});

const data = await response.json();

if (data.success) {
  // Store tokens
  localStorage.setItem('accessToken', data.accessToken);
  localStorage.setItem('refreshToken', data.refreshToken);
  
  // Use user data
  console.log('Logged in as:', data.user.fullName);
  console.log('Department:', data.user.departmentName);
  console.log('Position:', data.user.positionName);
  
  // Set auth header for subsequent requests
  const headers = {
    'Authorization': `Bearer ${data.accessToken}`,
    'Content-Type': 'application/json'
  };
}
```

## Postman Collection Example

```json
{
  "name": "Login with Employee Details",
  "request": {
    "method": "POST",
    "header": [
      {
        "key": "Content-Type",
        "value": "application/json"
      }
    ],
    "body": {
      "mode": "raw",
      "raw": "{\n  \"username\": \"seed.admin\",\n  \"password\": \"seed_hash_1\"\n}"
    },
    "url": {
      "raw": "http://localhost:8080/api/v1/auth/login",
      "protocol": "http",
      "host": ["localhost"],
      "port": "8080",
      "path": ["api", "v1", "auth", "login"]
    }
  }
}
```

