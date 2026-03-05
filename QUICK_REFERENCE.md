# HRMS API Quick Reference Guide

## 🚀 Quick Start

### Start the Server
```bash
cd backend
mvn spring-boot:run
```
Server runs on: `http://localhost:8080`

---

## 📡 API Base URL
```
http://localhost:8080/api
```

---

## 🔗 Quick Endpoint Reference

### Users - `/api/users`
```bash
POST   /users                    # Create
GET    /users                    # List all
GET    /users/{id}               # Get one
GET    /users/username/{name}    # Find by username
GET    /users/email/{email}      # Find by email
PUT    /users/{id}               # Update
DELETE /users/{id}               # Delete
```

### Roles - `/api/roles`
```bash
POST   /roles                    # Create
GET    /roles                    # List all
GET    /roles/{id}               # Get one
GET    /roles/name/{name}        # Find by name
PUT    /roles/{id}               # Update
DELETE /roles/{id}               # Delete
```

### Departments - `/api/departments`
```bash
POST   /departments              # Create
GET    /departments              # List all
GET    /departments/{id}         # Get one
GET    /departments/name/{name}  # Find by name
PUT    /departments/{id}         # Update
DELETE /departments/{id}         # Delete
```

### Positions - `/api/positions`
```bash
POST   /positions                        # Create
GET    /positions                        # List all
GET    /positions/{id}                   # Get one
GET    /positions/department/{deptId}    # By department
PUT    /positions/{id}                   # Update
DELETE /positions/{id}                   # Delete
```

### Employees - `/api/employees`
```bash
POST   /employees                        # Create
GET    /employees                        # List all
GET    /employees/{id}                   # Get one
GET    /employees/department/{deptId}    # By department
GET    /employees/status/{status}        # By status
PUT    /employees/{id}                   # Update
DELETE /employees/{id}                   # Delete
```

### Attendance - `/api/attendance`
```bash
POST   /attendance                       # Create
GET    /attendance                       # List all
GET    /attendance/{id}                  # Get one
GET    /attendance/employee/{empId}      # By employee
PUT    /attendance/{id}                  # Update
DELETE /attendance/{id}                  # Delete
```

### Leave Types - `/api/leave-types`
```bash
POST   /leave-types              # Create
GET    /leave-types              # List all
GET    /leave-types/{id}         # Get one
GET    /leave-types/name/{name}  # Find by name
PUT    /leave-types/{id}         # Update
DELETE /leave-types/{id}         # Delete
```

### Leave Requests - `/api/leave-requests`
```bash
POST   /leave-requests                   # Create
GET    /leave-requests                   # List all
GET    /leave-requests/{id}              # Get one
GET    /leave-requests/employee/{empId}  # By employee
GET    /leave-requests/status/{status}   # By status
PUT    /leave-requests/{id}              # Update
DELETE /leave-requests/{id}              # Delete
```

### Benefits - `/api/benefits`
```bash
POST   /benefits                 # Create
GET    /benefits                 # List all
GET    /benefits/{id}            # Get one
GET    /benefits/name/{name}     # Find by name
PUT    /benefits/{id}            # Update
DELETE /benefits/{id}            # Delete
```

### Employee Benefits - `/api/employee-benefits`
```bash
POST   /employee-benefits                # Create
GET    /employee-benefits                # List all
GET    /employee-benefits/{id}           # Get one
GET    /employee-benefits/employee/{id}  # By employee
PUT    /employee-benefits/{id}           # Update
DELETE /employee-benefits/{id}           # Delete
```

### Payroll - `/api/payroll`
```bash
POST   /payroll                          # Create
GET    /payroll                          # List all
GET    /payroll/{id}                     # Get one
GET    /payroll/employee/{empId}         # By employee
PUT    /payroll/{id}                     # Update
DELETE /payroll/{id}                     # Delete
```

### Audit Logs - `/api/audit-logs`
```bash
POST   /audit-logs                       # Create
GET    /audit-logs                       # List all
GET    /audit-logs/{id}                  # Get one
GET    /audit-logs/user/{userId}         # By user
GET    /audit-logs/table/{tableName}     # By table
GET    /audit-logs/action/{action}       # By action
```

---

## 📝 Sample cURL Commands

### Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "fullName": "John Doe",
    "email": "john@example.com",
    "passwordHash": "hashed123",
    "roleId": 1
  }'
```

### Get All Users
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Content-Type: application/json"
```

### Update a User
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "fullName": "John Doe Updated",
    "email": "john.updated@example.com",
    "passwordHash": "newhashed123",
    "roleId": 1
  }'
```

### Delete a User
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

### Create an Employee
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "departmentId": 1,
    "jobTitle": "Senior Developer",
    "employmentType": "Full-time",
    "salary": 50000.00,
    "hireDate": "2020-01-15",
    "status": "active"
  }'
```

### Create Leave Request
```bash
curl -X POST http://localhost:8080/api/leave-requests \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "leaveTypeId": 1,
    "startDate": "2026-03-10",
    "endDate": "2026-03-14",
    "reason": "Vacation",
    "status": "pending"
  }'
```

### Create Payroll
```bash
curl -X POST http://localhost:8080/api/payroll \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "baseSalary": 50000.00,
    "bonus": 5000.00,
    "deduction": 500.00,
    "payDate": "2026-03-31"
  }'
```

---

## 🔄 Common Status Codes

| Code | Meaning |
|------|---------|
| 200 | ✅ Success (GET/PUT) |
| 201 | ✅ Created (POST) |
| 204 | ✅ Success (DELETE) |
| 400 | ❌ Bad request |
| 404 | ❌ Not found |
| 500 | ❌ Server error |

---

## 📊 Common Status Values

### Employee Status
- `active` - Currently employed
- `inactive` - On leave/suspended
- `terminated` - Dismissed/Resigned

### Leave Request Status
- `pending` - Awaiting approval
- `approved` - Approved by manager
- `rejected` - Rejected by manager

### Attendance Status
- `present` - Attended
- `absent` - Missed
- `late` - Arrived late
- `early_leave` - Left early

---

## 💾 Database Configuration

Edit `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/hrms
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server
server.port=8080
server.servlet.context-path=/

# Logging
logging.level.root=INFO
logging.level.com.chanrady.hrms=DEBUG
```

---

## 🧪 Testing with Postman

### Import as REST API
1. Method: POST/GET/PUT/DELETE
2. URL: `http://localhost:8080/api/{endpoint}`
3. Headers: `Content-Type: application/json`
4. Body: JSON (for POST/PUT)

### Example Collection

```json
{
  "info": {
    "name": "HRMS API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Users",
      "item": [
        {
          "name": "Create User",
          "request": {
            "method": "POST",
            "url": "{{base_url}}/api/users"
          }
        }
      ]
    }
  ]
}
```

---

## 🔍 Troubleshooting

### Port Already in Use
```bash
# Change port in application.properties
server.port=8081
```

### Database Connection Error
```bash
# Verify PostgreSQL is running and credentials are correct
# Check spring.datasource.url, username, password
```

### Compilation Errors
```bash
# Clean and rebuild
mvn clean install

# Or just compile
mvn compile
```

---

## 📞 Quick Help

### View All Files
```bash
ls -la src/main/java/com/chanrady/hrms/
```

### Check Java Version
```bash
java -version
# Should show Java 21
```

### Maven Version
```bash
mvn -version
```

---

## ⚡ Performance Tips

1. **Use filters** to reduce data fetching
2. **Pagination** (future feature) for large datasets
3. **Lazy loading** reduces memory usage
4. **DTOs** prevent overfetching of relationships

---

## 📚 Documentation Files

- **API_DOCUMENTATION.md** - Complete API guide
- **ENDPOINTS_SUMMARY.md** - Summary of all endpoints
- **ENTITIES_DOCUMENTATION.md** - Entity relationships
- **IMPLEMENTATION_REPORT.md** - Full implementation report

---

## ✅ Checklist for Deployment

- [ ] Database configured and running
- [ ] Java 21 installed
- [ ] Maven installed
- [ ] Application starts without errors
- [ ] All endpoints respond correctly
- [ ] CORS is working
- [ ] Error handling is configured
- [ ] Logging is configured
- [ ] Security is configured (if needed)
- [ ] Documentation is reviewed

---

## 🚀 Production Checklist

- [ ] Authentication implemented
- [ ] Input validation added
- [ ] Error handling enhanced
- [ ] Logging configured
- [ ] HTTPS enabled
- [ ] Database backups configured
- [ ] Monitoring setup
- [ ] Rate limiting configured
- [ ] Tests written and passing
- [ ] API documentation generated (Swagger)

---

## 📞 Contact & Support

For issues or questions:
1. Check **API_DOCUMENTATION.md**
2. Review **ENTITIES_DOCUMENTATION.md**
3. Check application logs
4. Verify database connection

---

**HRMS Backend v1.0** - Ready for use! ✅

Last Updated: 2026-03-05

