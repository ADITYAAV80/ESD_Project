package com.aditya.project.Service;

import com.aditya.project.DTO.PlacementRequest;
import com.aditya.project.Model.*;
import com.aditya.project.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import java.util.List;

@Service
public class OfferService {

    private final PlacementRepository placementRepository;
    private final SpecializationRepository specializationRepository;
    private final DomainRepository domainRepository;
    private final PlacementFilterRepository placementFilterRepository;


    @Autowired
    public OfferService(PlacementRepository placementRepository,
                        SpecializationRepository specializationRepository,
                        DomainRepository domainRepository,
                        PlacementFilterRepository placementFilterRepository, AuthenticationManager authenticationManager) {
        this.placementRepository = placementRepository;
        this.specializationRepository = specializationRepository;
        this.domainRepository = domainRepository;
        this.placementFilterRepository = placementFilterRepository;
    }

    @Transactional
    public PlacementFilter savePlacementAndFilter(PlacementRequest request) {
        // Fetch the Specialization and Domain objects
        Specialization specialization = specializationRepository.findById(request.specialization_id())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        Domain domain = domainRepository.findById(request.domain_id())
                .orElseThrow(() -> new RuntimeException("Domain not found"));



        // Create the Placement object
        Placement placement = new Placement();
        placement.setOrganization(request.organization());
        placement.setProfile(request.profile());
        placement.setDescription(request.description());
        placement.setIntake(request.intake());
        placement.setMinimum_grade(request.minimumGrade());

        // Save the Placement entity and get the saved instance
        Placement savedPlacement = placementRepository.save(placement);

        // Create the PlacementFilter object
        PlacementFilter placementFilter = new PlacementFilter();
        placementFilter.setPlacement(savedPlacement);
        placementFilter.setSpecialization(specialization);
        placementFilter.setDomain(domain);

        // Save the PlacementFilter object
        return placementFilterRepository.save(placementFilter);
    }

    public List<Placement> getOffers(){
        return placementRepository.findAll();
    }

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    EmployeeRepository employeeRepository;

    public String verify(Employee employee) {

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(employee.getUsername())  ;
        } else {
            return "fail";
        }
    }
}


