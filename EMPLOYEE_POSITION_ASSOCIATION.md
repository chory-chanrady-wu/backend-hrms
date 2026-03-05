# ✅ Employee Position Association - JobTitle Linked to Position

## Overview

The `jobTitle` field in Employee has been replaced with a reference to the **Position** entity. Now employees are linked to specific positions by `positionId` instead of just storing job title text.

---

## What Changed

### Before
```java
@Column(name = "job_title", length = 100)
private String jobTitle;  // Just a string
```

### After
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "position_id")
private Position position;  // Reference to Position entity
```

---

## Employee Entity Structure

```java
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Required: Every employee has a user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;  // Optional: Department association

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;  // NEW: Position (job title) association

    @Column(name = "employment_type", length = 50)
    private String employmentType;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;

    // ... other fields
}
```

---

## Database Schema

### employees Table (New Column)
```sql
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    department_id INT,
    position_id INT,           -- NEW: References positions table
    employment_type VARCHAR(50),
    salary DECIMAL(10,2),
    hire_date DATE,
    status VARCHAR(30),
    image_url VARCHAR(500),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (position_id) REFERENCES positions(id)  -- NEW
);
```

---

## EmployeeDTO Changes

### Before
```java
private String jobTitle;  // Just text
```

### After
```java
private Integer positionId;      // Position reference
private String positionName;     // Position name (from Position.title)
```

---

## Request Body Examples

### Option 1: Create Employee with Existing User + Position
```json
{
  "userId": 1,
  "departmentId": 1,
  "positionId": 1,
  "employmentType": "Full-time",
  "salary": 80000,
  "hireDate": "2020-01-15",
  "status": "active"
}
```

**Result:**
- Uses existing user (ID 1)
- Uses existing position (ID 1) with job title
- Inserts into: `employees` table only

---

### Option 2: Create Employee with New User + Position
```json
{
  "username": "john.doe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "departmentId": 1,
  "positionId": 1,
  "employmentType": "Full-time",
  "salary": 80000,
  "hireDate": "2020-01-15",
  "status": "active"
}
```

**Result:**
- Creates new user automatically
- Uses existing position (ID 1)
- Inserts into: `users` table, then `employees` table

---

## API Endpoint

```
POST /api/v1/employees
```

### Request Body (Required Fields)
- `positionId` (Integer) - Required: ID of the position
- `employmentType` (String) - Employee type (Full-time, Part-time, Contract)
- `salary` (Decimal) - Salary amount
- `hireDate` (Date) - Hire date
- `status` (String) - Employee status (active, inactive, etc.)

### Optional Fields
- `userId` (Integer) - Use existing user
- `username` (String) - Create new user (required if no userId)
- `fullName` (String) - Full name (if creating new user)
- `email` (String) - Email (required if no userId)
- `departmentId` (Integer) - Department assignment
- `imageUrl` (String) - Employee profile image URL

---

## Response Example

```json
{
  "id": 1,
  "userId": 1,
  "username": "john.doe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "departmentId": 1,
  "departmentName": "Engineering",
  "positionId": 1,
  "positionName": "Senior Developer",
  "employmentType": "Full-time",
  "salary": 80000,
  "hireDate": "2020-01-15",
  "status": "active",
  "imageUrl": null,
  "createdAt": "2026-03-05T13:00:00",
  "updatedAt": "2026-03-05T13:00:00"
}
```

---

## Workflow Example

### Step 1: Create Department
```bash
POST /api/v1/departments
{
  "name": "Engineering",
  "description": "Software development team"
}
# Response: {"id": 1, "name": "Engineering", ...}
```

### Step 2: Create Position
```bash
POST /api/v1/positions
{
  "positionName": "Senior Developer",
  "description": "Lead developer responsible for architecture",
  "department": 1
}
# Response: {"id": 1, "positionName": "Senior Developer", "departmentId": 1, ...}
```

### Step 3: Create Employee with Position
```bash
POST /api/v1/employees
{
  "username": "john.doe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "departmentId": 1,
  "positionId": 1,
  "employmentType": "Full-time",
  "salary": 80000,
  "hireDate": "2020-01-15",
  "status": "active"
}
# Response: {"id": 1, "username": "john.doe", "positionId": 1, "positionName": "Senior Developer", ...}
```

---

## Entity Relationships

```
Position (1) ←→ (Many) Employee
Department (1) ←→ (Many) Employee
User (1) ←→ (1) Employee (One-to-One)
```

---

## Key Benefits

✅ **Structured Positions** - Employees linked to actual Position records  
✅ **Position Details** - Access position description, salary, department  
✅ **Data Consistency** - Position information centralized  
✅ **Easy Updates** - Change position without modifying employee  
✅ **Reporting** - Query positions by employee, employees by position  

---

## Query Examples

### Get All Employees with Their Positions
```
GET /api/v1/employees
```
Response includes positionId and positionName for each employee

### Get Employees in a Department
```
GET /api/v1/employees/department/1
```
Returns employees with their positions in that department

### Get Employees in a Position
No direct endpoint, but the data supports this query via position_id

---

## Migration Notes

If you have existing employees with job_title as text:

1. Position column (position_id) is added to employees table
2. Old job_title column can be removed after migration
3. Create Position records from existing job titles
4. Update employees to reference Position by ID

---

## Compilation Status

✅ **Build:** SUCCESS  
✅ **Errors:** None (Only IDE inspection warnings about missing DB tables at compile time)  
✅ **Status:** Ready for Testing

---

## Testing

```bash
# Create employee with position
curl -X POST http://localhost:7777/api/v1/employees \
  -H "Content-Type: application/json" \
  -d '{
    "username": "jane.smith",
    "fullName": "Jane Smith",
    "email": "jane@example.com",
    "departmentId": 1,
    "positionId": 1,
    "employmentType": "Full-time",
    "salary": 75000,
    "hireDate": "2021-01-15",
    "status": "active"
  }'
```

---

Date: 2026-03-05  
Status: ✅ Implemented & Ready  

