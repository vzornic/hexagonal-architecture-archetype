CREATE TABLE users
(
    id        UUID PRIMARY KEY,
    username  TEXT NOT NULL UNIQUE,
    email     TEXT NOT NULL UNIQUE,
    hashed_pw TEXT NOT NULL,
    salt      TEXT NOT NULL
);

CREATE TABLE example_entity
(
    id            UUID PRIMARY KEY,
    name          TEXT NOT NULL,
    description   TEXT
);