--liquibase formatted sql
--changeset Denis:1

INSERT INTO department(name, address, creation_date)
VALUES ('First', 'Address1', '2014-07-13 12:18:48');

INSERT INTO department(name, address, creation_date)
VALUES ('Second', 'Address2', '2018-02-04 19:18:48');

INSERT INTO department(name, address, creation_date)
VALUES ('Third', 'Address3', '2020-04-09 21:18:48');