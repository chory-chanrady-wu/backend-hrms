# 🔄 API Endpoints Update - /api to /api/v1

## ✅ Update Complete

All 12 REST controllers have been updated from `/api` to `/api/v1`.

---

## 📍 Updated Endpoints

### Before → After

| Controller | Before | After |
|------------|--------|-------|
| UserController | `/api/users` | `/api/v1/users` |
| RoleController | `/api/roles` | `/api/v1/roles` |
| DepartmentController | `/api/departments` | `/api/v1/departments` |
| PositionController | `/api/positions` | `/api/v1/positions` |
| EmployeeController | `/api/employees` | `/api/v1/employees` |
| AttendanceController | `/api/attendance` | `/api/v1/attendance` |
| LeaveTypeController | `/api/leave-types` | `/api/v1/leave-types` |
| LeaveRequestController | `/api/leave-requests` | `/api/v1/leave-requests` |
| BenefitController | `/api/benefits` | `/api/v1/benefits` |
| EmployeeBenefitController | `/api/employee-benefits` | `/api/v1/employee-benefits` |
| PayrollController | `/api/payroll` | `/api/v1/payroll` |
| AuditLogController | `/api/audit-logs` | `/api/v1/audit-logs` |
| CloudinaryController | `/api/v1/cloud/*` | ✅ Already v1 |

---

## 📋 New Base URLs

### Development
```
http://localhost:7777/api/v1
```

### Production
```
https://your-domain.com/api/v1
```

---

## 🔗 Complete Endpoint List

### User Management
```
POST   /api/v1/users
GET    /api/v1/users
GET    /api/v1/users/{id}
GET    /api/v1/users/username/{username}
GET    /api/v1/users/email/{email}
PUT    /api/v1/users/{id}
DELETE /api/v1/users/{id}
```

### Role Management
```
POST   /api/v1/roles
GET    /api/v1/roles
GET    /api/v1/roles/{id}
GET    /api/v1/roles/name/{roleName}
PUT    /api/v1/roles/{id}
DELETE /api/v1/roles/{id}
```

### Department Management
```
POST   /api/v1/departments
GET    /api/v1/departments
GET    /api/v1/departments/{id}
GET    /api/v1/departments/name/{name}
PUT    /api/v1/departments/{id}
DELETE /api/v1/departments/{id}
```

### Position Management
```
POST   /api/v1/positions
GET    /api/v1/positions
GET    /api/v1/positions/{id}
GET    /api/v1/positions/department/{departmentId}
PUT    /api/v1/positions/{id}
DELETE /api/v1/positions/{id}
```

### Employee Management
```
POST   /api/v1/employees
GET    /api/v1/employees
GET    /api/v1/employees/{id}
GET    /api/v1/employees/department/{departmentId}
GET    /api/v1/employees/status/{status}
PUT    /api/v1/employees/{id}
POST   /api/v1/employees/{id}/upload-image
DELETE /api/v1/employees/{id}
```

### Attendance Management
```
POST   /api/v1/attendance
GET    /api/v1/attendance
GET    /api/v1/attendance/{id}
GET    /api/v1/attendance/employee/{employeeId}
PUT    /api/v1/attendance/{id}
DELETE /api/v1/attendance/{id}
```

### Leave Type Management
```
POST   /api/v1/leave-types
GET    /api/v1/leave-types
GET    /api/v1/leave-types/{id}
GET    /api/v1/leave-types/name/{name}
PUT    /api/v1/leave-types/{id}
DELETE /api/v1/leave-types/{id}
```

### Leave Request Management
```
POST   /api/v1/leave-requests
GET    /api/v1/leave-requests
GET    /api/v1/leave-requests/{id}
GET    /api/v1/leave-requests/employee/{employeeId}
GET    /api/v1/leave-requests/status/{status}
PUT    /api/v1/leave-requests/{id}
DELETE /api/v1/leave-requests/{id}
```

### Benefit Management
```
POST   /api/v1/benefits
GET    /api/v1/benefits
GET    /api/v1/benefits/{id}
GET    /api/v1/benefits/name/{name}
PUT    /api/v1/benefits/{id}
DELETE /api/v1/benefits/{id}
```

### Employee Benefit Management
```
POST   /api/v1/employee-benefits
GET    /api/v1/employee-benefits
GET    /api/v1/employee-benefits/{id}
GET    /api/v1/employee-benefits/employee/{employeeId}
PUT    /api/v1/employee-benefits/{id}
DELETE /api/v1/employee-benefits/{id}
```

### Payroll Management
```
POST   /api/v1/payroll
GET    /api/v1/payroll
GET    /api/v1/payroll/{id}
GET    /api/v1/payroll/employee/{employeeId}
PUT    /api/v1/payroll/{id}
DELETE /api/v1/payroll/{id}
```

### Audit Log Management
```
POST   /api/v1/audit-logs
GET    /api/v1/audit-logs
GET    /api/v1/audit-logs/{id}
GET    /api/v1/audit-logs/user/{userId}
GET    /api/v1/audit-logs/table/{tableName}
GET    /api/v1/audit-logs/action/{action}
```

### Cloudinary Management
```
POST   /api/v1/cloud/upload
GET    /api/v1/cloud/verify
```

---

## 🧪 Testing Examples

### Test User Endpoint
```bash
# Old (will not work anymore)
curl http://localhost:7777/api/users

# New (correct)
curl http://localhost:7777/api/v1/users
```

### Create Role
```bash
curl -X POST http://localhost:7777/api/v1/roles \
  -H "Content-Type: application/json" \
  -d '{
    "roleName": "Admin",
    "permissions": "[\"read\", \"write\", \"delete\"]"
  }'
```

### Get All Employees
```bash
curl http://localhost:7777/api/v1/employees
```

### Upload Employee Image
```bash
curl -X POST http://localhost:7777/api/v1/employees/1/upload-image \
  -F "image=@photo.jpg"
```

---

## ✅ Status

- ✅ All 12 controllers updated
- ✅ Compilation successful
- ✅ No errors
- ✅ All endpoints now use `/api/v1` prefix

---

## 📚 Benefits of API Versioning

- ✅ Future-proof (can add /api/v2 later)
- ✅ Backward compatibility (can support multiple versions)
- ✅ Clear API version in URL
- ✅ Industry standard practice

---

## ⚠️ Important Notes

### Frontend Updates Required
If you have a frontend consuming these APIs, update all endpoint URLs from `/api/*` to `/api/v1/*`.

### Swagger/OpenAPI
Swagger UI will automatically detect the new endpoints at:
```
http://localhost:7777/swagger-ui/index.html
```

---

Date: 2026-03-05  
Status: ✅ Complete  
Controllers Updated: 12/12  
Compilation: SUCCESS  

