--liquibase formatted sql
--changeset Denis:1

INSERT INTO employee(name, surname, department_id)
VALUES ('Emp1', 'Sur1', '1');

INSERT INTO employee(name, surname, department_id)
VALUES ('Emp2', 'Sur2', '1');

INSERT INTO employee(name, surname, department_id)
VALUES ('Emp3', 'Sur3', '2');

INSERT INTO employee(name, surname, department_id)
VALUES ('Emp4', 'Sur4', '2');

INSERT INTO employee(name, surname, department_id)
VALUES ('Emp5', 'Sur5', '3');

INSERT INTO employee(name, surname, department_id)
VALUES ('Emp6', 'Sur6', '3');
