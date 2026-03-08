# Fix: Null Password Hash on User Update

## Problem
When updating a user with a PUT request that doesn't include `passwordHash`, the field was being set to NULL, which violated the database NOT NULL constraint.

## Error
```
ERROR: null value in column "password_hash" of relation "users" violates not-null constraint
```

## Root Cause
In `UserServiceImpl.updateUser()`, the password hash was being unconditionally set from the DTO:
```java
user.setPasswordHash(userDTO.getPasswordHash());  // ❌ Sets null if not provided
```

When a PUT request updates user data without including a password, it defaults to null and overwrites the existing password.

## Solution
Modified `updateUser()` to only update password if it's explicitly provided:
```java
// ✅ Only update password if provided and not empty
if (userDTO.getPasswordHash() != null && !userDTO.getPasswordHash().trim().isEmpty()) {
    user.setPasswordHash(userDTO.getPasswordHash());
}
```

## File Changed
- `UserServiceImpl.java` - Line 55-58

## How to Test

**Update user WITHOUT changing password:**
```bash
curl -X PUT http://localhost:7777/api/v1/users/4 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "seed.employee",
    "fullName": "Seed Employee Updated",
    "email": "seed.employee@hrms.local",
    "phoneNumber": "010346085",
    "roleId": 4
  }'
```
✅ Should work - password is NOT overwritten

**Update user WITH new password:**
```bash
curl -X PUT http://localhost:7777/api/v1/users/4 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "seed.employee",
    "fullName": "Seed Employee",
    "email": "seed.employee@hrms.local",
    "passwordHash": "new_password_hash",
    "phoneNumber": "010346085",
    "roleId": 4
  }'
```
✅ Should work - password IS updated

## Status
✅ **FIXED** - Code compiles, ready to test

## Similar Issue Prevention
Check other update methods for the same pattern and apply the same fix where needed.

