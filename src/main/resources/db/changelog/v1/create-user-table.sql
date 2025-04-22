CREATE TABLE IF NOT EXISTS "user" (
                                      id BIGSERIAL PRIMARY KEY,
                                      username VARCHAR NOT NULL UNIQUE
);