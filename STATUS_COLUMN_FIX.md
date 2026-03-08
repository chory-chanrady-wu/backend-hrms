# Fix PostgreSQL Boolean Status Column Issue

## Problem
The application encountered a type mismatch error when trying to cast empty strings to boolean:
```
Cannot cast to boolean: ""
```

This occurred because:
1. The database `users.status` and `employees.status` columns were originally VARCHAR (string)
2. We changed the entity type to `Boolean` in Java
3. Old data in the database still contains string values like `"active"`, `"inactive"`, or empty strings
4. PostgreSQL cannot convert these strings to boolean

## Solution

You have two options:

### Option 1: Run SQL Migration (Recommended)

Execute the migration script in your PostgreSQL database:

```sql
-- Connect to your HRMS database first
psql -U your_username -d hrms_database -f fix_status_columns.sql
```

OR manually in pgAdmin:

1. Open pgAdmin and connect to your database
2. Right-click the database → Query Tool
3. Copy and paste the SQL from `src/main/resources/fix_status_columns.sql`
4. Execute

This will:
- Create temporary boolean columns
- Convert all string values to boolean (active/true → true, inactive/false → false)
- Replace the old string columns with the new boolean columns
- Set default value to `true` for null entries

### Option 2: Drop and Recreate Database (Alternative)

If you want a fresh start:

```sql
-- DROP the entire database and recreate
DROP DATABASE IF EXISTS hrms;
CREATE DATABASE hrms;
```

Then restart the application. Hibernate will automatically create all tables with correct types.

## What Changed in Code

1. **EmployeeServiceImpl.getEmployeesByStatus()** - Fixed to query Boolean values directly instead of converting to string
2. **User Entity** - Status is now `Boolean` (not nullable, default true)
3. **Employee Entity** - Status is now `Boolean` (not nullable, default true)

## Value Mapping

After migration:
- `active`, `true`, `yes`, `1` → `true`
- `inactive`, `false`, `no`, `0` → `false`
- NULL → `true` (default)

## Testing

After applying the migration, test the endpoints:

```bash
# Get all users (should work now)
curl -X GET http://localhost:7777/api/v1/users

# Get all employees
curl -X GET http://localhost:7777/api/v1/employees

# Filter by status
curl -X GET http://localhost:7777/api/v1/employees/status/active
```

## If You Still Get Errors

Make sure to:
1. Stop the application completely
2. Apply the SQL migration
3. Start the application fresh

The Hibernate `ddl-auto: update` setting won't fix existing bad data - you need the SQL migration.

