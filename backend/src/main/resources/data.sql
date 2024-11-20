-- Inserting data into Department table
INSERT INTO department (department_id, name, capacity) VALUES
(1, 'Outreach', 50);

-- Inserting data into Employee table
INSERT INTO employee (employee_id, username, password,email,title, department) VALUES
(1, 'rajan', 'rajan','rajan@gmail.com','Outreach Officer', 1),
(2, 'pralay', 'pralay','pralay@gmail','Outreach Officer', 1),
(3, 'nikhil', 'nikhil', 'nikhil@gmail.com','Student Placement officer',1);

-- Inserting data into Domain table
INSERT INTO domain (domain_id, program, batch, capacity, qualification) VALUES
(1, 'Mtech', '2024', 60, 'Master'),
(2, 'Imtech', '2024', 70, 'Master'),
(3, 'Btech', '2024', 80, 'Bachelor');

-- Inserting data into Specialization table
INSERT INTO specialization (specialization_id, code, name, description, year, credits_required) VALUES
(1, 'AI101', 'AI ML', 'AI and Machine Learning', '2024', 30),
(2, 'CS101', 'CS', 'Computer Science', '2025', 25),
(3, 'EC101', 'EC', 'Electronics and Communication', '2024', 20);

-- Inserting data into Placement table
INSERT INTO placement (id, organization, profile, description, intake, minimum_grade) VALUES
(1, 'Google', 'Software Engineer', 'Backend Development', 10, 3.6),
(2, 'Tesla', 'Data Analyst', 'Data Analyst', 8, 3.4),
(3, 'GE', 'VLSI Designer', 'VLSI Designer', 12, 3.0);

-- Inserting data into PlacementFilter table
INSERT INTO placement_filter (id, placement_id, specialization, domain) VALUES
(1, 1, 1, 1),
(2, 2, 2, 2),
(3, 3, 3, 3);

INSERT INTO organization (id, name, address) VALUES
(1, 'Google', '1600 Amphitheatre Parkway, Mountain View, CA'),
(2, 'Microsoft', 'One Microsoft Way, Redmond, WA'),
(3, 'Apple', 'One Apple Park Way, Cupertino, CA');