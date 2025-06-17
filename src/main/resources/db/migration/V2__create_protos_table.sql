-- V2__create_protos_table.sql
CREATE TABLE protos (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    catchcopy TEXT NOT NULL,
    concept TEXT NOT NULL,
    image TEXT,
    user_id INTEGER NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);