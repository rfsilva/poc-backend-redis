CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE person (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    address VARCHAR(255),
    phone_number VARCHAR(50)
);