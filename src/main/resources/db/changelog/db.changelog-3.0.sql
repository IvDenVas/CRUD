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
-- INSERT INTO employee(name, surname, date_of_birth)
-- VALUES ('Emp1', 'Sur1', '1990-07-13 12:18:48');
--
-- INSERT INTO employee(name, surname, date_of_birth)
-- VALUES ('Emp2', 'Sur2', '1991-08-19 12:18:48');
--
-- INSERT INTO employee(name, surname, date_of_birth)
-- VALUES ('Emp3', 'Sur3', '1993-03-01 12:18:48');
--
-- INSERT INTO employee(name, surname, date_of_birth)
-- VALUES ('Emp4', 'Sur4', '1987-11-19 12:18:48');
--
-- INSERT INTO employee(name, surname, date_of_birth)
-- VALUES ('Emp5', 'Sur5', '1985-10-23 12:18:48');
--
-- INSERT INTO employee(name, surname, date_of_birth)
-- VALUES ('Emp6', 'Sur6', '1980-01-03 12:18:48');