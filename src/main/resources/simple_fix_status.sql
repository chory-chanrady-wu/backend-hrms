-- Simple fix for status columns - convert to boolean properly
-- First, make status nullable to allow updates
ALTER TABLE users ALTER COLUMN status DROP NOT NULL;
ALTER TABLE employees ALTER COLUMN status DROP NOT NULL;

-- Convert all empty strings and invalid values to NULL
UPDATE users SET status = NULL WHERE status = '' OR status IS NULL OR LOWER(status) NOT IN ('true', 'false', 'active', 'inactive');
UPDATE employees SET status = NULL WHERE status = '' OR status IS NULL OR LOWER(status) NOT IN ('true', 'false', 'active', 'inactive');

-- Set defaults for NULL values
UPDATE users SET status = 'true' WHERE status IS NULL;
UPDATE employees SET status = 'true' WHERE status IS NULL;

-- Now make them not null again
ALTER TABLE users ALTER COLUMN status SET NOT NULL;
ALTER TABLE employees ALTER COLUMN status SET NOT NULL;

