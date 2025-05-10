create table if not exists "user"  (
    id BIGSERIAL primary key,
    username VARCHAR(100) unique not null
);