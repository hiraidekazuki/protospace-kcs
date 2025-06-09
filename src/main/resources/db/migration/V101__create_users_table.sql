CREATE TABLE users (
    id        SERIAL       NOT NULL,
    email     VARCHAR(128)    NOT NULL UNIQUE, /*一意性指定*/
    password  VARCHAR(512)    NOT NULL,
    name      VARCHAR(128)    NOT NULL,
    profile   TEXT            NOT NULL,
    group_name VARCHAR(128)    NOT NULL, /*所属*/
    post      VARCHAR(128)    NOT NULL, /*役職*/
    PRIMARY KEY (id)
);