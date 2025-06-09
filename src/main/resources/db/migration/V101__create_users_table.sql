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