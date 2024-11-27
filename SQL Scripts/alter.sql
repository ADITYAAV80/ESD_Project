ALTER TABLE Employee
ADD CONSTRAINT fk_department FOREIGN KEY (department) REFERENCES Department(department_id);

ALTER TABLE PlacementFilter
ADD CONSTRAINT fk_placement FOREIGN KEY (placement_id) REFERENCES Placement(id);

ALTER TABLE PlacementFilter
ADD CONSTRAINT fk_specialization FOREIGN KEY (specialization) REFERENCES Specialization(specialization_id);

ALTER TABLE PlacementFilter
ADD CONSTRAINT fk_domain FOREIGN KEY (domain) REFERENCES Domain(domain_id);

