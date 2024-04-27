--liquibase formatted sql
--changeset Denis:1
CREATE TABLE IF NOT EXISTS department(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    modification_date TIMESTAMP
);
--rollback DROP TABLE department;