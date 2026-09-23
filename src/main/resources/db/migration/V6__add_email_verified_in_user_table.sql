ALTER TABLE users RENAME COLUMN password TO password_hash;

ALTER TABLE users ADD COLUMN email_verified BOOLEAN DEFAULT FALSE;