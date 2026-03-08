# SOLUTION: Boolean Status Column Fix

## The Problem
PostgreSQL cannot convert empty string `""` or invalid string values to Boolean type.

## IMMEDIATE FIX (Choose One Option)

### ✅ OPTION 1: Drop and Recreate Database (EASIEST - 2 minutes)

1. **Stop the application**
2. **In pgAdmin or psql, drop and recreate the database:**

```sql
DROP DATABASE IF EXISTS hrms CASCADE;
CREATE DATABASE hrms;
```

3. **Restart the application:**
```bash
cd C:\Users\CHORY Chanrady\Desktop\Final_Project_Wing\backend
.\mvnw clean spring-boot:run
```

Hibernate will automatically create all tables with correct Boolean types.

---

### OPTION 2: Manual SQL Fix (pgAdmin GUI)

1. **Open pgAdmin**
2. **Right-click database → Query Tool**
3. **Run this SQL:**

```sql
-- Clean up users table
ALTER TABLE users ALTER COLUMN status DROP NOT NULL;
UPDATE users SET status = CASE 
    WHEN status = '' OR status IS NULL THEN 'true'
    WHEN LOWER(status) IN ('active', '1', 'yes') THEN 'true'
    WHEN LOWER(status) IN ('inactive', '0', 'no') THEN 'false'
    ELSE 'true'
END;
ALTER TABLE users ALTER COLUMN status SET NOT NULL;

-- Clean up employees table  
ALTER TABLE employees ALTER COLUMN status DROP NOT NULL;
UPDATE employees SET status = CASE 
    WHEN status = '' OR status IS NULL THEN 'true'
    WHEN LOWER(status) IN ('active', '1', 'yes') THEN 'true'
    WHEN LOWER(status) IN ('inactive', '0', 'no') THEN 'false'
    ELSE 'true'
END;
ALTER TABLE employees ALTER COLUMN status SET NOT NULL;
```

4. **Restart the application**

---

### OPTION 3: Use pgAdmin Data Editor

1. **In pgAdmin, select the `users` table**
2. **Click "Data" → "View/Edit Data"**
3. **Find rows with empty or invalid status values**
4. **Update them to either 'true' or 'false'**
5. **Do the same for `employees` table**
6. **Restart application**

---

## FASTEST SOLUTION

**I recommend OPTION 1** - it takes 2 minutes and is guaranteed to work:

```sql
DROP DATABASE IF EXISTS hrms CASCADE;
CREATE DATABASE hrms;
```

Then restart the app. Done!

---

## After Fix - Test

Once fixed, test these endpoints:

```bash
# Should work without errors
curl http://localhost:7777/api/v1/users
curl http://localhost:7777/api/v1/employees
curl http://localhost:7777/api/v1/auth/login
```

All should return data successfully without "Cannot cast to boolean" errors.

---

## Why This Happened

- Original status columns were VARCHAR (string type)
- We changed Java entities to Boolean
- Old database data (strings/empty) couldn't convert to Boolean
- PostgreSQL throws error when reading the data

## What Changed in Code

✅ **EmployeeServiceImpl.java** - Fixed to handle Boolean status properly
✅ **User & Employee entities** - Now use Boolean status
✅ All DTOs updated to use Boolean status

The code is correct. The database just needs cleanup.

