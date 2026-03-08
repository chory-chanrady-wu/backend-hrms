-- Fix User status column - convert string values to boolean
ALTER TABLE users DROP COLUMN IF EXISTS status_temp CASCADE;
ALTER TABLE users ADD COLUMN status_temp BOOLEAN;

-- Convert existing string status values to boolean
UPDATE users SET status_temp = true WHERE LOWER(status) IN ('active', 'true', 'yes', '1');
UPDATE users SET status_temp = false WHERE LOWER(status) IN ('inactive', 'false', 'no', '0');
UPDATE users SET status_temp = true WHERE status IS NULL; -- Default to true for null values

-- Drop old column and rename new one
ALTER TABLE users DROP COLUMN status;
ALTER TABLE users RENAME COLUMN status_temp TO status;

-- Set not null constraint
ALTER TABLE users ALTER COLUMN status SET NOT NULL;
ALTER TABLE users ALTER COLUMN status SET DEFAULT true;

-- Fix Employee status column - convert string values to boolean
ALTER TABLE employees DROP COLUMN IF EXISTS status_temp CASCADE;
ALTER TABLE employees ADD COLUMN status_temp BOOLEAN;

-- Convert existing string status values to boolean
UPDATE employees SET status_temp = true WHERE LOWER(status) IN ('active', 'true', 'yes', '1');
UPDATE employees SET status_temp = false WHERE LOWER(status) IN ('inactive', 'false', 'no', '0');
UPDATE employees SET status_temp = true WHERE status IS NULL; -- Default to true for null values

-- Drop old column and rename new one
ALTER TABLE employees DROP COLUMN status;
ALTER TABLE employees RENAME COLUMN status_temp TO status;

-- Set not null constraint
ALTER TABLE employees ALTER COLUMN status SET NOT NULL;
ALTER TABLE employees ALTER COLUMN status SET DEFAULT true;

