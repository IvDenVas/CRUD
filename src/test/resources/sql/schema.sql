DROP TABLE IF EXISTS department CASCADE ;
DROP TABLE IF EXISTS employee CASCADE ;

CREATE TABLE IF NOT EXISTS department(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    modification_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS employee(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    department_id BIGINT NOT NULL REFERENCES department(id)
);


INSERT INTO department(name, address, creation_date)
VALUES ('Test1', 'Test1', '2014-07-13 12:18:48');

INSERT INTO department(name, address, creation_date)
VALUES ('Test2', 'Test2', '2018-02-04 19:18:48');

INSERT INTO department(name, address, creation_date)
VALUES ('Test3', 'Test3', '2020-04-09 21:18:48');



INSERT INTO employee(name, surname, department_id)
VALUES ('EmpTest1', 'SurTest1', 1);

INSERT INTO employee(name, surname, department_id)
VALUES ('EmpTest2', 'SurTest2', 1);

INSERT INTO employee(name, surname, department_id)
VALUES ('EmpTest3', 'SurTest3', 2);

INSERT INTO employee(name, surname, department_id)
VALUES ('EmpTest4', 'SurTest4', 2);

INSERT INTO employee(name, surname, department_id)
VALUES ('EmpTest5', 'SurTest5', 3);

INSERT INTO employee(name, surname, department_id)
VALUES ('EmpTest6', 'SurTest6', 3);