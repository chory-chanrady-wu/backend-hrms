# ✅ Employee Image URL - Complete Guide

## Overview

The Employee entity includes an `imageUrl` field that stores the Cloudinary secure URL of the employee's profile image/photo.

---

## Implementation

### Employee Entity
```java
@Column(name = "image_url", length = 500)
private String imageUrl;
```

**Details:**
- Column name: `image_url` in database
- Data type: VARCHAR(500)
- Nullable: YES (optional field)
- Storage: Cloudinary secure URL

### Employee DTO
```java
private String imageUrl;
```

**Available in:**
- Request body (when creating/updating)
- Response (when getting employee data)

---

## Database Schema

```sql
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    department_id INT,
    position_id INT,
    employment_type VARCHAR(50),
    salary DECIMAL(10,2),
    hire_date DATE,
    status VARCHAR(30),
    image_url VARCHAR(500),      -- Employee profile image URL
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (department_id) REFERENCES departments(id),
    FOREIGN KEY (position_id) REFERENCES positions(id)
);
```

---

## How to Upload Employee Image

### Endpoint: Upload Image for Specific Employee
```
POST /api/v1/employees/{id}/upload-image
```

**Parameters:**
- `{id}` - Employee ID (path parameter)
- `image` - Image file (form data, multipart)

### Request Example
```bash
curl -X POST http://localhost:7777/api/v1/employees/1/upload-image \
  -F "image=@employee-photo.jpg"
```

### What Happens
1. Image file is uploaded to Cloudinary
2. Cloudinary returns `secure_url` (HTTPS URL)
3. Employee's `imageUrl` is updated with this URL
4. Database record is saved with new URL
5. Full EmployeeDTO is returned with updated imageUrl

### Response Example
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
  "imageUrl": "https://res.cloudinary.com/your-cloud/image/upload/v1709700600/abc123def456.jpg",
  "createdAt": "2026-03-05T13:00:00",
  "updatedAt": "2026-03-05T13:05:00"
}
```

---

## Create Employee with Image URL

### Option 1: Direct URL (Pre-uploaded)
```json
{
  "username": "john.doe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "positionId": 1,
  "departmentId": 1,
  "employmentType": "Full-time",
  "salary": 80000,
  "hireDate": "2020-01-15",
  "status": "active",
  "imageUrl": "https://res.cloudinary.com/your-cloud/image/upload/v1709700600/existing-image.jpg"
}
```

**Use Case:** Image already uploaded to Cloudinary, just link it

---

### Option 2: Upload After Creation
```bash
# Step 1: Create employee WITHOUT image
POST /api/v1/employees
{
  "username": "john.doe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "positionId": 1,
  "departmentId": 1,
  "employmentType": "Full-time",
  "salary": 80000,
  "hireDate": "2020-01-15",
  "status": "active"
}
# Response: {"id": 1, "imageUrl": null, ...}

# Step 2: Upload image for this employee
POST /api/v1/employees/1/upload-image
-F "image=@photo.jpg"
# Response: {"id": 1, "imageUrl": "https://res.cloudinary.com/.../photo.jpg", ...}
```

---

## Update Employee Image

### Replace Existing Image
```bash
POST /api/v1/employees/1/upload-image \
  -F "image=@new-photo.jpg"
```

**Result:**
- Old `imageUrl` is replaced
- New image stored in Cloudinary
- Employee record updated with new URL

---

## Supported Image Formats

✅ JPEG (.jpg, .jpeg)  
✅ PNG (.png)  
✅ GIF (.gif)  
✅ WebP (.webp)  
✅ BMP (.bmp)  
✅ TIFF (.tiff)  

**Max File Size:** 10MB (configurable in application properties)

---

## API Flow Diagram

```
1. Client selects image file
                ↓
2. POST /api/v1/employees/{id}/upload-image with image
                ↓
3. EmployeeController receives request
                ↓
4. EmployeeService.updateEmployeeImage() called
                ↓
5. CloudinaryImageService.upload(file) uploads to Cloudinary
                ↓
6. Cloudinary returns: {secure_url: "https://...", ...}
                ↓
7. Extract secure_url from response
                ↓
8. employee.setImageUrl(secure_url)
                ↓
9. employeeRepository.save(employee)
                ↓
10. Return EmployeeDTO with imageUrl
                ↓
11. Client receives updated employee with image URL
```

---

## Service Implementation

### In EmployeeServiceImpl
```java
@Override
public EmployeeDTO updateEmployeeImage(Integer id, MultipartFile image) throws IOException {
    Optional<Employee> existingEmployee = employeeRepository.findById(id);
    if (existingEmployee.isPresent()) {
        Employee employee = existingEmployee.get();
        
        // Upload image to Cloudinary
        Map<String, Object> uploadResult = cloudinaryImageService.upload(image);
        String secureUrl = (String) uploadResult.get("secure_url");
        
        // Update employee with image URL
        employee.setImageUrl(secureUrl);
        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToDTO(updatedEmployee);
    }
    return null;
}
```

### In convertToDTO
```java
private EmployeeDTO convertToDTO(Employee employee) {
    EmployeeDTO dto = new EmployeeDTO();
    // ... other mappings ...
    dto.setImageUrl(employee.getImageUrl());  // Include imageUrl
    return dto;
}
```

---

## Complete Workflow Example

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
  "description": "Lead developer",
  "department": 1
}
# Response: {"id": 1, "positionName": "Senior Developer", "departmentId": 1, ...}
```

### Step 3: Create Employee
```bash
POST /api/v1/employees
{
  "username": "john.doe",
  "fullName": "John Doe",
  "email": "john@example.com",
  "positionId": 1,
  "departmentId": 1,
  "employmentType": "Full-time",
  "salary": 80000,
  "hireDate": "2020-01-15",
  "status": "active"
}
# Response: {"id": 1, "username": "john.doe", "imageUrl": null, ...}
```

### Step 4: Upload Employee Image
```bash
POST /api/v1/employees/1/upload-image \
  -F "image=@john-photo.jpg"
# Response: {"id": 1, "username": "john.doe", "imageUrl": "https://res.cloudinary.com/.../john-photo.jpg", ...}
```

### Step 5: Get Employee with Image
```bash
GET /api/v1/employees/1
# Response: {"id": 1, "imageUrl": "https://res.cloudinary.com/.../john-photo.jpg", ...}
```

---

## Get Employee with Image URL

### Get Single Employee
```bash
GET /api/v1/employees/1
```

**Response includes:**
```json
{
  "id": 1,
  "userId": 1,
  "username": "john.doe",
  "imageUrl": "https://res.cloudinary.com/your-cloud/image/upload/v1709700600/abc123.jpg",
  // ... other fields ...
}
```

### Get All Employees
```bash
GET /api/v1/employees
```

**Response includes imageUrl for each employee**

### Get Employees by Department
```bash
GET /api/v1/employees/department/1
```

**Response includes imageUrl for each employee in department**

---

## Frontend Integration Example

### React/JavaScript
```javascript
// Upload employee image
async function uploadEmployeeImage(employeeId, imageFile) {
    const formData = new FormData();
    formData.append('image', imageFile);
    
    const response = await fetch(
        `http://localhost:7777/api/v1/employees/${employeeId}/upload-image`,
        {
            method: 'POST',
            body: formData
        }
    );
    
    const employee = await response.json();
    return employee.imageUrl;  // Cloudinary secure URL
}

// Display employee with image
function displayEmployee(employee) {
    if (employee.imageUrl) {
        const img = document.createElement('img');
        img.src = employee.imageUrl;
        img.alt = employee.fullName;
        img.style.width = '100px';
        img.style.height = '100px';
        document.body.appendChild(img);
    }
}
```

### HTML Form
```html
<form action="http://localhost:7777/api/v1/employees/1/upload-image" 
      method="POST" 
      enctype="multipart/form-data">
    <input type="file" name="image" accept="image/*" required>
    <button type="submit">Upload Photo</button>
</form>

<!-- Display employee image -->
<img src="https://res.cloudinary.com/your-cloud/image/upload/v1709700600/abc123.jpg" 
     alt="Employee Photo"
     width="100"
     height="100">
```

---

## Testing with cURL

### Upload Image
```bash
curl -X POST http://localhost:7777/api/v1/employees/1/upload-image \
  -F "image=@/path/to/photo.jpg"
```

### Get Employee with Image
```bash
curl http://localhost:7777/api/v1/employees/1
```

### Test Different Image Formats
```bash
# JPEG
curl -X POST http://localhost:7777/api/v1/employees/1/upload-image \
  -F "image=@photo.jpg"

# PNG
curl -X POST http://localhost:7777/api/v1/employees/1/upload-image \
  -F "image=@photo.png"

# WebP
curl -X POST http://localhost:7777/api/v1/employees/1/upload-image \
  -F "image=@photo.webp"
```

---

## Important Notes

### Image URL Storage
- ✅ Full HTTPS URL stored in database
- ✅ Secure URL provided by Cloudinary
- ✅ Can be used directly in HTML `<img>` tags
- ✅ No authentication required to view image

### Image Upload Behavior
- ✅ Old image URL is replaced (not deleted from Cloudinary)
- ✅ Can upload new image anytime
- ✅ Image file not stored in database, only URL
- ✅ Cloudinary handles image optimization

### Security
- ✅ File validation by Cloudinary
- ✅ Only image files accepted
- ✅ Max 10MB file size enforced
- ✅ Secure HTTPS URLs only

---

## Postman Collection

Import `HRMS_Postman_Collection.json` which includes:

### Upload Employee Image Request
```
POST /api/v1/employees/1/upload-image
Body: form-data
  - Key: image
  - Value: [select file]
```

---

## Database Considerations

### Initial State
```
Employee created: imageUrl = NULL
```

### After Image Upload
```
Employee updated: imageUrl = "https://res.cloudinary.com/..."
```

### Image URL Format
```
https://res.cloudinary.com/{cloud-name}/image/upload/v{timestamp}/{public-id}.{format}
```

Example:
```
https://res.cloudinary.com/hrms-storage/image/upload/v1709700600/employees/john_doe_abc123.jpg
```

---

## Error Handling

### Employee Not Found
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Employee with ID 999 not found"
}
```

### Invalid File
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid file format or corrupted file"
}
```

### Cloudinary Error
```json
{
  "status": 502,
  "error": "Cloudinary Upload Failed",
  "message": "Invalid Cloudinary configuration or upload failure"
}
```

---

## Status

✅ **Implementation:** Complete  
✅ **Database Column:** `image_url` VARCHAR(500)  
✅ **DTO Field:** `imageUrl` String  
✅ **Upload Endpoint:** POST /api/v1/employees/{id}/upload-image  
✅ **Cloudinary Integration:** Working  
✅ **Documentation:** Complete  

---

Date: 2026-03-05  
Status: ✅ Full Image URL Support Implemented  

