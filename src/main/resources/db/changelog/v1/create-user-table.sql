create table "user" (
    id BigSerial primary key,
    username varchar not null unique
);