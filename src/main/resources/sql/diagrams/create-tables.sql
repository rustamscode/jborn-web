CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255)   NOT NULL,
    user_id BIGINT REFERENCES users (id),
    balance NUMERIC(10, 2) NOT NULL,
    date    TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions
(
    id          BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES categories (id),
    account_id  BIGINT REFERENCES accounts (id)
);