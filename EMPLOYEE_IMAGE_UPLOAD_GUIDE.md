# 📸 Employee Image Upload Implementation Guide

## ✅ Implementation Complete

Employee image upload functionality has been successfully integrated with Cloudinary. The `image_url` field now stores the secure URL from Cloudinary uploads.

---

## 🔄 What Was Changed

### 1. Employee Entity - Added Image URL Field
**File:** `src/main/java/com/chanrady/hrms/entity/Employee.java`

Added field:
```java
@Column(name = "image_url", length = 500)
private String imageUrl;
```

### 2. Employee DTO - Added Image URL Field
**File:** `src/main/java/com/chanrady/hrms/dto/EmployeeDTO.java`

Added field:
```java
private String imageUrl;
```

### 3. Employee Service - Added Image Upload Method
**File:** `src/main/java/com/chanrady/hrms/service/EmployeeService.java`

Added method:
```java
EmployeeDTO updateEmployeeImage(Integer id, MultipartFile image) throws IOException;
```

### 4. Employee Service Implementation
**File:** `src/main/java/com/chanrady/hrms/service/EmployeeServiceImpl.java`

- Injected `CloudinaryImageService`
- Implemented `updateEmployeeImage()` method
- Updates `imageUrl` field with Cloudinary's `secure_url`
- Saves employee record with new image URL

### 5. Employee Controller - Added Image Upload Endpoint
**File:** `src/main/java/com/chanrady/hrms/controller/EmployeeController.java`

Added endpoint:
```java
@PostMapping("/{id}/upload-image")
public ResponseEntity<EmployeeDTO> uploadEmployeeImage(
    @PathVariable Integer id,
    @RequestParam("image") MultipartFile image) throws IOException
```

---

## 📍 New Endpoint

### Upload Employee Image
```
POST /api/employees/{id}/upload-image
```

**Description:** Upload an employee's profile image to Cloudinary and store the secure URL in the employee record.

**Request:**
- Method: `POST`
- Content-Type: `multipart/form-data`
- URL Parameter: `id` (Employee ID)
- Form Field: `image` (image file)
- Max file size: 10MB

**Example with cURL:**
```bash
curl -X POST http://localhost:7777/api/employees/1/upload-image \
  -F "image=@/path/to/employee-photo.jpg"
```

**Example with PowerShell:**
```powershell
$uri = "http://localhost:7777/api/employees/1/upload-image"
$filePath = "C:\path\to\employee-photo.jpg"

$multipartContent = [System.Net.Http.MultipartFormDataContent]::new()
$fileStream = [System.IO.File]::OpenRead($filePath)
$fileContent = [System.Net.Http.StreamContent]::new($fileStream)
$multipartContent.Add($fileContent, "image", [System.IO.Path]::GetFileName($filePath))

$response = Invoke-RestMethod -Uri $uri -Method Post -Body $multipartContent
$response | ConvertTo-Json
```

**Success Response (200 OK):**
```json
{
    "id": 1,
    "userId": 1,
    "username": "john.doe",
    "fullName": "John Doe",
    "email": "john@example.com",
    "departmentId": 1,
    "departmentName": "Engineering",
    "jobTitle": "Senior Developer",
    "employmentType": "Full-time",
    "salary": 50000.00,
    "hireDate": "2020-01-15",
    "status": "active",
    "imageUrl": "https://res.cloudinary.com/your-cloud/image/upload/v1709619123/abc123def456.jpg",
    "createdAt": "2020-01-15T10:30:00",
    "updatedAt": "2026-03-05T11:15:00"
}
```

**Error Response (404 Not Found):**
```json
{
    "timestamp": "2026-03-05T11:15:00",
    "status": 404,
    "error": "Not Found",
    "message": "Employee not found"
}
```

**Error Response (502 Bad Gateway - Cloudinary Error):**
```json
{
    "timestamp": "2026-03-05T11:15:00",
    "status": 502,
    "error": "Cloudinary Upload Failed",
    "message": "Invalid Cloudinary configuration or upload failure: ..."
}
```

---

## 🔄 How It Works

### Flow Diagram
```
1. Client sends POST /api/employees/{id}/upload-image with image file
              ↓
2. EmployeeController receives request
              ↓
3. EmployeeService.updateEmployeeImage() is called
              ↓
4. CloudinaryImageService.upload() uploads image to Cloudinary
              ↓
5. Cloudinary returns response with secure_url
              ↓
6. Employee.imageUrl is updated with secure_url
              ↓
7. Employee record is saved to database
              ↓
8. EmployeeDTO with imageUrl is returned to client
```

### Code Flow
```java
// 1. Upload to Cloudinary
Map<String, Object> uploadResult = cloudinaryImageService.upload(image);

// 2. Extract secure_url
String secureUrl = (String) uploadResult.get("secure_url");

// 3. Update employee record
employee.setImageUrl(secureUrl);
employeeRepository.save(employee);
```

---

## 📝 Database Changes

### Migration Required
After starting the application, Hibernate will automatically add the new column:

```sql
ALTER TABLE employees ADD COLUMN image_url VARCHAR(500);
```

This happens automatically with `spring.jpa.hibernate.ddl-auto=update` in your configuration.

---

## 🧪 Testing Workflow

### Step 1: Create an Employee (if needed)
```bash
curl -X POST http://localhost:7777/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "departmentId": 1,
    "jobTitle": "Developer",
    "employmentType": "Full-time",
    "salary": 50000,
    "hireDate": "2020-01-15",
    "status": "active"
  }'
```

Response will include `"id": 1` (employee ID).

### Step 2: Upload Employee Image
```bash
curl -X POST http://localhost:7777/api/employees/1/upload-image \
  -F "image=@employee-photo.jpg"
```

### Step 3: Verify Image URL is Stored
```bash
curl http://localhost:7777/api/employees/1
```

Response should include:
```json
{
    "id": 1,
    "imageUrl": "https://res.cloudinary.com/your-cloud/image/upload/v.../file.jpg",
    ...
}
```

### Step 4: Update Image (Upload New One)
```bash
curl -X POST http://localhost:7777/api/employees/1/upload-image \
  -F "image=@new-photo.jpg"
```

The old `imageUrl` will be replaced with the new one.

---

## 📋 Supported File Types

Cloudinary accepts:
- ✅ JPEG (.jpg, .jpeg)
- ✅ PNG (.png)
- ✅ GIF (.gif)
- ✅ WebP (.webp)
- ✅ BMP (.bmp)
- ✅ TIFF (.tiff)
- ✅ And more...

Maximum file size: **10MB** (configured in `application-dev.yaml`)

---

## 🎯 Use Cases

### 1. Upload Employee Profile Picture
```bash
POST /api/employees/1/upload-image
```

### 2. Update Employee Profile Picture
```bash
POST /api/employees/1/upload-image
# (Same endpoint - overwrites previous imageUrl)
```

### 3. Get Employee with Image URL
```bash
GET /api/employees/1
```

Returns employee data including `imageUrl` field.

### 4. Create Employee with Image URL (Manual)
```bash
POST /api/employees
{
    "imageUrl": "https://res.cloudinary.com/...",
    ...
}
```

---

## 🔗 Related Endpoints

### Cloudinary Direct Upload (Alternative)
```
POST /api/v1/cloud/upload
```

Upload any image and get Cloudinary response, then manually set `imageUrl` when creating/updating employee.

**Workflow:**
1. Upload image → get `secure_url`
2. Create/update employee with `imageUrl` field set

### Employee Upload (Integrated)
```
POST /api/employees/{id}/upload-image
```

Upload image for specific employee - automatically stores `secure_url`.

---

## 🎨 Frontend Integration Examples

### React/JavaScript Example
```javascript
async function uploadEmployeeImage(employeeId, file) {
    const formData = new FormData();
    formData.append('image', file);
    
    const response = await fetch(
        `http://localhost:7777/api/employees/${employeeId}/upload-image`,
        {
            method: 'POST',
            body: formData
        }
    );
    
    const employee = await response.json();
    console.log('Image URL:', employee.imageUrl);
    return employee;
}

// Usage
const file = document.querySelector('input[type="file"]').files[0];
const updatedEmployee = await uploadEmployeeImage(1, file);
```

### HTML Form Example
```html
<form action="http://localhost:7777/api/employees/1/upload-image" 
      method="POST" 
      enctype="multipart/form-data">
    <input type="file" name="image" accept="image/*" required>
    <button type="submit">Upload Photo</button>
</form>
```

---

## 📊 Updated Employee Model

### Employee Entity Fields
```java
class Employee {
    Integer id;
    User user;
    Department department;
    String jobTitle;
    String employmentType;
    BigDecimal salary;
    LocalDate hireDate;
    String status;
    String imageUrl;           // ← NEW FIELD
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime deletedAt;
}
```

### Employee DTO Fields
```java
class EmployeeDTO {
    Integer id;
    Integer userId;
    String username;
    String fullName;
    String email;
    Integer departmentId;
    String departmentName;
    String jobTitle;
    String employmentType;
    BigDecimal salary;
    LocalDate hireDate;
    String status;
    String imageUrl;           // ← NEW FIELD
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
```

---

## 🔧 Configuration Requirements

### Prerequisites
- Cloudinary account with valid credentials
- `.env` file configured with real Cloudinary values:

```dotenv
CLOUDINARY_CLOUD_NAME=your_real_cloud_name
CLOUDINARY_API_KEY=your_real_api_key
CLOUDINARY_API_SECRET=your_real_api_secret
```

### Verify Config
```bash
curl http://localhost:7777/api/v1/cloud/verify
```

---

## 🎯 Summary

### What's Implemented ✅

| Feature | Status |
|---------|--------|
| `image_url` field in Employee entity | ✅ |
| `imageUrl` field in EmployeeDTO | ✅ |
| Upload employee image endpoint | ✅ |
| Cloudinary integration | ✅ |
| Secure URL extraction | ✅ |
| Database storage | ✅ |
| Error handling | ✅ |
| CORS support | ✅ |

### Endpoints Available

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/employees/{id}/upload-image` | Upload employee image |
| POST | `/api/v1/cloud/upload` | Direct Cloudinary upload |
| GET | `/api/v1/cloud/verify` | Verify Cloudinary config |
| GET | `/api/employees/{id}` | Get employee (includes imageUrl) |
| POST | `/api/employees` | Create employee (can include imageUrl) |
| PUT | `/api/employees/{id}` | Update employee (can include imageUrl) |

---

## 🚀 Quick Test

```bash
# 1. Verify Cloudinary is configured
curl http://localhost:7777/api/employees/1/upload-image \
  -F "image=@photo.jpg"

# 2. Check employee has imageUrl
curl http://localhost:7777/api/employees/1
```

Expected response includes:
```json
{
    "id": 1,
    "imageUrl": "https://res.cloudinary.com/...",
    ...
}
```

---

## 📝 Notes

### Image URL Storage
- Field: `image_url` (database), `imageUrl` (Java/JSON)
- Type: VARCHAR(500)
- Nullable: Yes (employee can exist without image)
- Format: Full Cloudinary secure URL

### Image Upload Behavior
- Old image URL is replaced (not deleted from Cloudinary)
- Image is uploaded with `resource_type=auto`
- Returns full employee DTO with updated `imageUrl`

### Security
- CORS enabled for cross-origin requests
- Max file size: 10MB
- Cloudinary handles file validation

---

## ⚠️ Important

**Before testing uploads, ensure you have valid Cloudinary credentials in `.env`!**

See `CLOUDINARY_SETUP_GUIDE.md` for credential setup instructions.

---

Date: 2026-03-05  
Status: ✅ Complete & Ready for Testing  

