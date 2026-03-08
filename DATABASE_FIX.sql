-- ============================================
-- DATABASE FIX FOR BOOLEAN STATUS COLUMNS
-- Copy and paste this into pgAdmin Query Tool
-- ============================================

-- STEP 1: Fix USERS table
ALTER TABLE users ALTER COLUMN status DROP NOT NULL;
UPDATE users SET status = 'true' WHERE status = '' OR status IS NULL OR LOWER(status) NOT IN ('true', 'false');
UPDATE users SET status = 'true' WHERE LOWER(status) IN ('active', 'yes', '1');
UPDATE users SET status = 'false' WHERE LOWER(status) IN ('inactive', 'no', '0');
ALTER TABLE users ALTER COLUMN status SET NOT NULL;
ALTER TABLE users ALTER COLUMN status SET DEFAULT true;

-- STEP 2: Fix EMPLOYEES table
ALTER TABLE employees ALTER COLUMN status DROP NOT NULL;
UPDATE employees SET status = 'true' WHERE status = '' OR status IS NULL OR LOWER(status) NOT IN ('true', 'false');
UPDATE employees SET status = 'true' WHERE LOWER(status) IN ('active', 'yes', '1');
UPDATE employees SET status = 'false' WHERE LOWER(status) IN ('inactive', 'no', '0');
ALTER TABLE employees ALTER COLUMN status SET NOT NULL;
ALTER TABLE employees ALTER COLUMN status SET DEFAULT true;

-- STEP 3: Verify the fix
SELECT COUNT(*) as total_users, COUNT(status) as valid_status FROM users;
SELECT COUNT(*) as total_employees, COUNT(status) as valid_status FROM employees;

-- If counts match, the fix worked!

