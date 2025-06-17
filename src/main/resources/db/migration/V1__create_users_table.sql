
-- V1__create_users_table.sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    profile TEXT,
    group_name VARCHAR(255),
    post TEXT
);

CREATE TABLE users (
    id        SERIAL       NOT NULL,
    email     VARCHAR(128)    NOT NULL UNIQUE, 
    password  VARCHAR(512)    NOT NULL,
    name      VARCHAR(128)    NOT NULL,
    profile   TEXT            NOT NULL,
    group_name VARCHAR(128)    NOT NULL, 
    post      VARCHAR(128)    NOT NULL, 
    PRIMARY KEY (id)
);

