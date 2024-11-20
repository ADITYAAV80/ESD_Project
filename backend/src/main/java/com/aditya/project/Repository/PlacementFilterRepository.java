package com.aditya.project.Repository;

import com.aditya.project.Model.Domain;
import com.aditya.project.Model.Placement;
import com.aditya.project.Model.PlacementFilter;
import com.aditya.project.Model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlacementFilterRepository extends JpaRepository<PlacementFilter, Integer> {

}
