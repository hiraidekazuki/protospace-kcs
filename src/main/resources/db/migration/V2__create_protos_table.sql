CREATE TABLE protos (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    catchcopy TEXT NOT NULL,
    concept TEXT NOT NULL,
    image TEXT, -- ファイル名やURLなどを格納
    user_name TEXT NOT NULL
);