# ✅ Seed Data - Fixed and Working

## Status

The `SeedDataInitializer` has been fixed and will now work correctly on application startup.

---

## What Was Fixed

### Problem
The original SeedDataInitializer was using incorrect all-args constructors:
```java
// ❌ WRONG - Too many parameters, wrong order
new Department(null, "Engineering", "...", null, null, null, null, null)
```

### Solution
Replaced with proper setter-based initialization:
```java
// ✅ CORRECT - Use setters
Department d1 = new Department();
d1.setName("Engineering");
d1.setDescription("...");
```

---

## Fixed Entities

✅ **Departments** - 5 seed records
- Engineering
- Human Resources
- Finance
- Marketing
- Operations

✅ **LeaveTypes** - 5 seed records
- Annual Leave
- Sick Leave
- Maternity Leave
- Paternity Leave
- Unpaid Leave

✅ **Benefits** - 5 seed records
- Health Insurance
- Transport Allowance
- Meal Allowance
- Phone Allowance
- Annual Bonus

✅ **Positions** - 5 seed records
- Senior Developer
- HR Specialist
- Accountant
- Marketing Executive
- Operations Officer

✅ **Roles** - 5 seed records
- ADMIN
- HR
- MANAGER
- EMPLOYEE
- ACCOUNTANT

✅ **Users** - 5 seed records (auto-created)
- seed.admin
- seed.hr
- seed.manager
- seed.employee
- seed.accountant

✅ **Employees** - 5 seed records
- All linked to users and positions

✅ **Attendance** - 5 seed records
✅ **LeaveRequests** - 5 seed records
✅ **Payroll** - 5 seed records
✅ **EmployeeBenefits** - 5 seed records
✅ **AuditLogs** - 5 seed records

---

## How It Works

### Activation
The initializer is enabled with:
```java
@Component
@Profile({"dev", "local"})
public class SeedDataInitializer implements CommandLineRunner
```

### Trigger Profiles
It runs automatically when you start the application with:
- `spring.profiles.active=dev` or
- `spring.profiles.active=local`

### Idempotent Check
```java
if (roleRepository.count() > 0 || userRepository.count() > 0 || employeeRepository.count() > 0) {
    return;  // Skip seeding if data already exists
}
```

### Seed Order
Data is inserted in dependency order:
1. **Roles** (no dependencies)
2. **Departments** (no dependencies)
3. **LeaveTypes** (no dependencies)
4. **Benefits** (no dependencies)
5. **Positions** (depends on Department)
6. **Users** (depends on Role)
7. **Employees** (depends on User, Department, Position)
8. **Attendance** (depends on Employee)
9. **LeaveRequests** (depends on Employee, LeaveType)
10. **Payroll** (depends on Employee)
11. **EmployeeBenefits** (depends on Employee, Benefit)
12. **AuditLogs** (depends on User)

---

## Using Seed Data

### Option 1: Run with Dev Profile
```bash
cd C:\Users\CHORY Chanrady\Desktop\Final_Project_Wing\backend
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Option 2: Run with Local Profile
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
```

### Option 3: Application Properties
In `application.properties`:
```properties
spring.profiles.active=dev
```

Or in `application-dev.yaml`:
```yaml
spring:
  profiles:
    active: dev
```

---

## Test the Seed Data

### Get All Users
```bash
curl http://localhost:7777/api/v1/users
```

### Expected Response
```json
[
  {
    "id": 1,
    "username": "seed.admin",
    "fullName": "Seed Admin",
    "email": "seed.admin@hrms.local",
    ...
  },
  {
    "id": 2,
    "username": "seed.hr",
    "fullName": "Seed HR",
    "email": "seed.hr@hrms.local",
    ...
  },
  ...
]
```

### Get All Employees
```bash
curl http://localhost:7777/api/v1/employees
```

### Get All Departments
```bash
curl http://localhost:7777/api/v1/departments
```

### Get All Positions
```bash
curl http://localhost:7777/api/v1/positions
```

---

## Seed Data Overview

### Total Records Created
- **Roles:** 5
- **Departments:** 5
- **LeaveTypes:** 5
- **Benefits:** 5
- **Positions:** 5
- **Users:** 5
- **Employees:** 5
- **Attendance:** 5
- **LeaveRequests:** 5
- **Payroll:** 5
- **EmployeeBenefits:** 5
- **AuditLogs:** 5

**Total: 60 records across 12 tables**

---

## Sample Credentials (After Seeding)

You can login with these users:

| Username | Full Name | Email | Role | Password |
|----------|-----------|-------|------|----------|
| seed.admin | Seed Admin | seed.admin@hrms.local | ADMIN | seed_hash_1 |
| seed.hr | Seed HR | seed.hr@hrms.local | HR | seed_hash_2 |
| seed.manager | Seed Manager | seed.manager@hrms.local | MANAGER | seed_hash_3 |
| seed.employee | Seed Employee | seed.employee@hrms.local | EMPLOYEE | seed_hash_4 |
| seed.accountant | Seed Accountant | seed.accountant@hrms.local | ACCOUNTANT | seed_hash_5 |

**Note:** Passwords are hash placeholders. In production, use proper password hashing.

---

## File Location

```
src/main/java/com/chanrady/hrms/util/SeedDataInitializer.java
```

---

## Compilation Status

✅ **Build:** SUCCESS  
✅ **Errors:** None  
✅ **Status:** Ready to Deploy

---

## Next Steps

1. Start application with `dev` or `local` profile
2. Seed data will auto-populate on startup
3. Access data via APIs with endpoints like:
   - `GET /api/v1/employees`
   - `GET /api/v1/departments`
   - `GET /api/v1/users`
   - etc.

---

Date: 2026-03-05  
Status: ✅ Seed Data Fixed & Ready  

