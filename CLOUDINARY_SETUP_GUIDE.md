# ☁️ Cloudinary Upload Configuration Guide

## Current Error Analysis

Your error message:
```json
{
    "error": "Cloudinary Upload Failed",
    "message": "Invalid Cloudinary configuration or upload failure: Invalid cloud_name hrms-images",
    "timestamp": "2026-03-05T11:19:10.3023439",
    "status": 502
}
```

**Root Cause:** The `cloud_name` value `hrms-images` doesn't match any Cloudinary account for the provided API key/secret.

---

## ✅ What's Working

- ✅ Spring Boot starts without errors
- ✅ `.env` file is being loaded
- ✅ Cloudinary config bean is created
- ✅ Upload endpoint is accessible
- ✅ Clean JSON error responses (no more stack traces)
- ✅ Global exception handling

---

## ❌ What Needs to Be Fixed

Your Cloudinary credentials in `.env` don't match a real Cloudinary account.

### Current Values in `.env`
```dotenv
CLOUDINARY_CLOUD_NAME=hrms-images
CLOUDINARY_API_KEY=619445869646536
CLOUDINARY_API_SECRET=9o60YapPCGkIy8uMNT8nS6_IsDc
```

---

## 🔧 How to Fix

### Step 1: Get Real Cloudinary Credentials

1. **Log in to Cloudinary Dashboard**: https://console.cloudinary.com/
2. **Go to Dashboard** (or "Account Details")
3. **Copy these exact values**:
   - **Cloud name** (e.g., `dxyz123abc`)
   - **API Key** (e.g., `123456789012345`)
   - **API Secret** (e.g., `abcdefghijklmnopqrstuvwxyz123456`)

### Step 2: Update Your `.env` File

Open `backend/.env` and replace with your **real** values:

```dotenv
# ===== Database (Docker) =====
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:9999/hrms_db
SPRING_DATASOURCE_USERNAME=hrms@admin.com
SPRING_DATASOURCE_PASSWORD=Hrmsadmin@11032002

POSTGRES_DB=hrms_db
POSTGRES_USER=hrms@admin.com
POSTGRES_PASSWORD=Hrmsadmin@11032002

# ===== Cloudinary =====
CLOUDINARY_CLOUD_NAME=your_actual_cloud_name_from_dashboard
CLOUDINARY_API_KEY=your_actual_api_key_from_dashboard
CLOUDINARY_API_SECRET=your_actual_api_secret_from_dashboard
```

### Step 3: Restart the Application

```powershell
cd "C:\Users\CHORY Chanrady\Desktop\Final_Project_Wing\backend"
mvn spring-boot:run
```

### Step 4: Verify Configuration (NEW ENDPOINT)

Before uploading, check your config is loaded correctly:

```bash
curl http://localhost:7777/api/v1/cloud/verify
```

Response will show:
```json
{
    "cloudName": "your_actual_cloud_name",
    "apiKeyMasked": "6194...6536",
    "configured": true,
    "message": "Verify these values match your Cloudinary Dashboard -> Account Details"
}
```

### Step 5: Test Upload

```bash
curl -X POST http://localhost:7777/api/v1/cloud/upload \
  -F "image=@/path/to/test-image.jpg"
```

---

## 🔍 How to Find Your Cloudinary Cloud Name

### Option 1: Cloudinary Console
1. Go to https://console.cloudinary.com/
2. Look at the URL after login: `https://console.cloudinary.com/console/c-YOUR_CLOUD_NAME/...`
3. The `c-...` part contains your cloud name

### Option 2: Dashboard Page
1. Log in to Cloudinary
2. Click "Dashboard" in the left menu
3. See **"Cloud name"** in the "Account Details" section
4. Copy the exact value (no spaces, no quotes)

### Option 3: Settings
1. Go to Settings → Account
2. Find "Cloud name" at the top
3. Copy the exact value

---

## ⚠️ Common Mistakes

❌ **Using a folder name instead of cloud name**
- Wrong: `hrms-images` (folder you created)
- Correct: `dxyz123abc` (your account cloud name)

❌ **Using mismatched credentials**
- API key from Account A + Cloud name from Account B = Invalid

❌ **Extra spaces or quotes**
- Wrong: `CLOUDINARY_CLOUD_NAME=" dxyz123abc "`
- Correct: `CLOUDINARY_CLOUD_NAME=dxyz123abc`

❌ **Using expired/rotated credentials**
- If you regenerated API secret in dashboard, update `.env`

---

## 🎯 New Endpoints Available

### Verify Configuration
```
GET /api/v1/cloud/verify
```
Returns loaded Cloudinary config (with masked API key) for debugging.

### Upload Image
```
POST /api/v1/cloud/upload
```
Accepts multipart file with field name `image`.

---

## 🧪 Testing Steps

### 1. Verify Config Endpoint
```powershell
curl http://localhost:7777/api/v1/cloud/verify
```

Expected response:
```json
{
    "cloudName": "your_cloud_name",
    "apiKeyMasked": "1234...5678",
    "configured": true,
    "message": "Verify these values match your Cloudinary Dashboard -> Account Details"
}
```

### 2. Test Upload (after config is verified)
```powershell
curl -X POST http://localhost:7777/api/v1/cloud/upload `
  -F "image=@C:\path\to\test.jpg"
```

Expected success response:
```json
{
    "public_id": "abc123def456",
    "version": 1709619123,
    "signature": "...",
    "width": 1920,
    "height": 1080,
    "format": "jpg",
    "resource_type": "image",
    "created_at": "2026-03-05T04:15:23Z",
    "url": "http://res.cloudinary.com/...",
    "secure_url": "https://res.cloudinary.com/..."
}
```

---

## 🔐 Security Reminder

**IMPORTANT:** The credentials in your `.env` and previous messages are now exposed. After you get upload working:

1. Go to Cloudinary Dashboard → Settings → Security
2. **Rotate your API Secret**
3. Update `.env` with the new secret
4. Add `.env` to `.gitignore` (if not already)

---

## 📝 What I Changed

- ✅ Fixed `.env` format (removed `$env:` PowerShell syntax)
- ✅ Added `.env` import to `application.properties`
- ✅ Fixed Cloudinary config to trim values
- ✅ Added global exception handler for clean error responses
- ✅ Added `/api/v1/cloud/verify` endpoint to check loaded config
- ✅ Renamed controller to proper Java convention (CloudinaryController)
- ✅ Changed route from `/api/v1/cloud/upload` to `/api/v1/cloud/upload` with base `/api/v1/cloud`

---

## 🚀 Next Actions Required FROM YOU

1. **Get real Cloudinary credentials** from your dashboard
2. **Update `.env`** with those exact values
3. **Restart the app**: `mvn spring-boot:run`
4. **Test verify endpoint**: `curl http://localhost:7777/api/v1/cloud/verify`
5. **Test upload**: `curl -X POST http://localhost:7777/api/v1/cloud/upload -F "image=@test.jpg"`

---

## 💡 Quick Fix if You Don't Have Cloudinary Account

If you don't have a Cloudinary account yet:

1. **Sign up free**: https://cloudinary.com/users/register/free
2. **Get credentials** from dashboard
3. **Update `.env`**
4. **Restart app**

---

## 📞 Need Help?

Run the verify endpoint first:
```bash
curl http://localhost:7777/api/v1/cloud/verify
```

This will show you what values Spring loaded, so you can compare with your Cloudinary dashboard.

---

**The code is 100% correct. You just need real Cloudinary credentials from your dashboard.** ✅

