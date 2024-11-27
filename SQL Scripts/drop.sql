-- Drop foreign key constraints first
ALTER TABLE PlacementFilter DROP FOREIGN KEY fk_placement;
ALTER TABLE PlacementFilter DROP FOREIGN KEY fk_specialization;
ALTER TABLE PlacementFilter DROP FOREIGN KEY fk_domain;

-- Drop foreign key constraint for Employee
ALTER TABLE Employee DROP FOREIGN KEY fk_department;

-- Drop the tables
DROP TABLE IF EXISTS PlacementFilter;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Placement;
DROP TABLE IF EXISTS Organization;
DROP TABLE IF EXISTS Domain;
DROP TABLE IF EXISTS Specialization;
DROP TABLE IF EXISTS Department;

