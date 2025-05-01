CREATE TABLE IF NOT EXISTS "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_user_username ON "user" (username);