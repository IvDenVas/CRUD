-- CREATE SEQUENCE department_id_seq START WITH 4 INCREMENT 1;
CREATE TABLE IF NOT EXISTS department(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL ,
    address VARCHAR(50) NOT NULL ,
    creation_date VARCHAR(50) NOT NULL ,
    modification_date VARCHAR(50)
    );

INSERT INTO department(name, address, creation_date, modification_date)
VALUES ('First', 'Address1', '24-04-24 09:18:48', null);

INSERT INTO department(name, address, creation_date, modification_date)
VALUES ('Second', 'Address2', '24-04-24 09:18:48', null);

INSERT INTO department(name, address, creation_date, modification_date)
VALUES ('Third', 'Address3', '24-04-24 09:18:48', null);