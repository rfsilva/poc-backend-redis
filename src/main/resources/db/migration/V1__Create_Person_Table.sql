-- Criação da extensão UUID se não existir
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação da tabela person com todos os campos necessários
CREATE TABLE person (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    address VARCHAR(255),
    phone_number VARCHAR(50),
    cpf VARCHAR(11),
    nationality VARCHAR(3) NOT NULL DEFAULT 'BRA',
    passport VARCHAR(20),
    gender VARCHAR(1)
);

-- Adiciona restrição: passaporte obrigatório para não-brasileiros
ALTER TABLE person ADD CONSTRAINT check_passport_required 
CHECK (nationality = 'BRA' OR passport IS NOT NULL);