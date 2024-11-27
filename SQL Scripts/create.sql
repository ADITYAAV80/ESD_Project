CREATE TABLE Department (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE Specialization (
    specialization_id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    year VARCHAR(4),
    credits_required INT NOT NULL
);

CREATE TABLE Domain (
    domain_id INT AUTO_INCREMENT PRIMARY KEY,
    program VARCHAR(255) NOT NULL,
    batch VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    qualification VARCHAR(255) NOT NULL
);

CREATE TABLE Organization (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE Placement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    organization VARCHAR(255) NOT NULL,
    profile VARCHAR(255) NOT NULL,
    description TEXT,
    intake INT,
    minimum_grade FLOAT
);

CREATE TABLE Employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255),
    department INT -- We will link this to the department later via foreign key
);

CREATE TABLE PlacementFilter (
    id INT AUTO_INCREMENT PRIMARY KEY,
    placement_id INT,  -- Link to Placement table
    specialization INT, -- Link to Specialization table, nullable
    domain INT -- Link to Domain table, nullable
);
