DROP TABLE IF EXISTS department;

CREATE TABLE IF NOT EXISTS department(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    modification_date TIMESTAMP
);

INSERT INTO department(name, address, creation_date)
VALUES ('Department1', 'Address1', '2014-07-13 12:18:48');

INSERT INTO department(name, address, creation_date)
VALUES ('Department2', 'Address2', '2018-02-04 19:18:48');

INSERT INTO department(name, address, creation_date)
VALUES ('Department3', 'Address3', '2020-04-09 21:18:48');