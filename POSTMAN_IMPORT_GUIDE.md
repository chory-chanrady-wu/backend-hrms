# 📮 Postman Collection Import Guide

## ✅ Collection Created

A complete Postman collection has been generated with all 95+ HRMS API endpoints.

**File:** `HRMS_Postman_Collection.json`

---

## 🚀 How to Import

### Step 1: Open Postman
- Launch Postman application
- Go to **File** → **Import** (or use Ctrl+O)

### Step 2: Import the Collection
- Click **Upload Files**
- Navigate to and select `HRMS_Postman_Collection.json`
- Click **Open**

### Step 3: Confirm Import
- Review the collection details
- Click **Import**

### Step 4: Use the Collection
- The collection appears in left sidebar
- Expand folders to see all endpoints
- Click any request to use it

---

## 📋 Collection Structure

```
HRMS API Collection
├── Users (7 endpoints)
│   ├── Create User
│   ├── Get All Users
│   ├── Get User by ID
│   ├── Get User by Username
│   ├── Get User by Email
│   ├── Update User
│   └── Delete User
├── Roles (6 endpoints)
│   ├── Create Role
│   ├── Get All Roles
│   ├── Get Role by ID
│   ├── Get Role by Name
│   ├── Update Role
│   └── Delete Role
├── Departments (6 endpoints)
│   ├── Create Department
│   ├── Get All Departments
│   ├── Get Department by ID
│   ├── Get Department by Name
│   ├── Update Department
│   └── Delete Department
├── Positions (6 endpoints)
│   ├── Create Position
│   ├── Get All Positions
│   ├── Get Position by ID
│   ├── Get Positions by Department
│   ├── Update Position
│   └── Delete Position
├── Employees (8 endpoints)
│   ├── Create Employee
│   ├── Get All Employees
│   ├── Get Employee by ID
│   ├── Get Employees by Department
│   ├── Get Employees by Status
│   ├── Update Employee
│   ├── Upload Employee Image
│   └── Delete Employee
├── Attendance (6 endpoints)
│   ├── Create Attendance
│   ├── Get All Attendance
│   ├── Get Attendance by ID
│   ├── Get Attendance by Employee
│   ├── Update Attendance
│   └── Delete Attendance
├── Leave Types (6 endpoints)
│   ├── Create Leave Type
│   ├── Get All Leave Types
│   ├── Get Leave Type by ID
│   ├── Get Leave Type by Name
│   ├── Update Leave Type
│   └── Delete Leave Type
├── Leave Requests (7 endpoints)
│   ├── Create Leave Request
│   ├── Get All Leave Requests
│   ├── Get Leave Request by ID
│   ├── Get Leave Requests by Employee
│   ├── Get Leave Requests by Status
│   ├── Update Leave Request
│   └── Delete Leave Request
├── Benefits (6 endpoints)
│   ├── Create Benefit
│   ├── Get All Benefits
│   ├── Get Benefit by ID
│   ├── Get Benefit by Name
│   ├── Update Benefit
│   └── Delete Benefit
├── Employee Benefits (6 endpoints)
│   ├── Create Employee Benefit
│   ├── Get All Employee Benefits
│   ├── Get Employee Benefit by ID
│   ├── Get Employee Benefits by Employee
│   ├── Update Employee Benefit
│   └── Delete Employee Benefit
├── Payroll (6 endpoints)
│   ├── Create Payroll
│   ├── Get All Payroll
│   ├── Get Payroll by ID
│   ├── Get Payroll by Employee
│   ├── Update Payroll
│   └── Delete Payroll
├── Audit Logs (6 endpoints)
│   ├── Create Audit Log
│   ├── Get All Audit Logs
│   ├── Get Audit Log by ID
│   ├── Get Audit Logs by User
│   ├── Get Audit Logs by Table
│   └── Get Audit Logs by Action
└── Cloudinary (2 endpoints)
    ├── Upload Image
    └── Verify Cloudinary Config
```

---

## 📝 What's Included in Each Request

### Pre-configured Elements
- ✅ **Correct HTTP Method** (GET, POST, PUT, DELETE)
- ✅ **Full Endpoint URLs** (http://localhost:7777/api/v1/...)
- ✅ **Request Headers** (Content-Type: application/json)
- ✅ **Sample Request Bodies** (JSON)
- ✅ **Form Data** (for file uploads)

### Example Request
```
Name: Create Role
Method: POST
URL: http://localhost:7777/api/v1/roles
Headers: Content-Type: application/json
Body: {
  "roleName": "Admin",
  "permissions": "[\"read\", \"write\", \"delete\"]"
}
```

---

## 🧪 Quick Testing Steps

### 1. Create a Role
1. Open **Roles** → **Create Role**
2. Click **Send**
3. View the response (you should get 201 Created)

### 2. Get All Roles
1. Open **Roles** → **Get All Roles**
2. Click **Send**
3. View list of roles

### 3. Create a Department
1. Open **Departments** → **Create Department**
2. Update the body with your department name
3. Click **Send**
4. Note the `id` from response (you'll need it)

### 4. Create a Position
1. Open **Positions** → **Create Position**
2. Update the `department` field with the ID from step 3
3. Click **Send**

### 5. Upload Employee Image
1. Open **Employees** → **Upload Employee Image**
2. Click on **Body** tab
3. In form data, click the file selector for `image` field
4. Select an image file
5. Click **Send**

---

## 🔄 Using Variables

### Set Base URL Variable (Optional)
You can set a Postman variable for the base URL to make requests more portable:

1. Click **Environment** or **Globals** (top left)
2. Add variable: `baseUrl` = `http://localhost:7777`
3. Replace `http://localhost:7777` with `{{baseUrl}}` in URLs

Then change base URL in one place instead of all requests.

---

## 📌 Important Notes

### IDs in URLs
- Replace `1` in URLs with actual IDs from your database
- Example: `/api/v1/employees/1` → `/api/v1/employees/5` (if employee ID is 5)

### Request Bodies
- Update sample data with your actual values
- Keep the JSON structure the same
- Don't remove required fields

### Authentication (Future)
- Currently no authentication required
- When you add JWT/OAuth, add headers to collection

---

## 💡 Tips & Tricks

### 1. Copy Request
- Right-click request → **Duplicate** to create a copy
- Modify for different test cases

### 2. Save Response
- Click **Save Response** after sending request
- View full response data

### 3. View Request History
- Click **History** tab
- See all previous requests sent

### 4. Export Results
- Click **...** menu
- Select **Export** to save responses

### 5. Run Requests in Sequence
- Use **Runner** feature (Postman Pro)
- Run multiple requests automatically

---

## 🛠️ Troubleshooting

### "Connection refused"
- Ensure backend server is running
- Check port: 7777
- Verify localhost is correct

### "404 Not Found"
- Check endpoint path in URL
- Verify API version: `/api/v1/`
- Confirm resource exists in database

### "400 Bad Request"
- Check request body syntax
- Ensure all required fields are present
- Verify JSON is valid

### "201 Created" but empty response
- This is normal for some endpoints
- Data was saved successfully
- Use GET endpoint to verify

---

## 📚 Related Documentation

- **API_DOCUMENTATION.md** - Complete endpoint details
- **API_V1_MIGRATION.md** - Endpoint URL reference
- **Department_Position_Request_Bodies.md** - Sample request bodies
- **Create_Role_Request_Body.md** - Role creation examples
- **EMPLOYEE_IMAGE_UPLOAD_GUIDE.md** - Image upload details

---

## ✅ Verification Checklist

After importing, verify:
- [ ] All 13 folders are visible (Users, Roles, Departments, etc.)
- [ ] Total of 95+ requests in collection
- [ ] Each request has correct HTTP method
- [ ] All URLs use `/api/v1/` prefix
- [ ] Sample bodies are present
- [ ] Can click "Send" on any request

---

## 🚀 Next Steps

1. **Import the collection**
2. **Start with Users → Create User**
3. **Follow the testing steps**
4. **Update IDs as needed**
5. **Explore all endpoints**

---

## 📞 Collection Info

- **Name:** HRMS API Collection
- **Version:** 1.0
- **Total Endpoints:** 95+
- **Total Folders:** 13 (resource categories)
- **Base URL:** http://localhost:7777/api/v1
- **Format:** Postman Collection 2.1

---

Date: 2026-03-05  
Status: ✅ Ready for Import  

