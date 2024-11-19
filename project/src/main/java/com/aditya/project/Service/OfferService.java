package com.aditya.project.Service;

import com.aditya.project.DTO.DomainResponse;
import com.aditya.project.DTO.PlacementRequest;
import com.aditya.project.DTO.SpecializationResponse;
import com.aditya.project.Model.*;
import com.aditya.project.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                        PlacementFilterRepository placementFilterRepository){//, AuthenticationManager authenticationManager) {
        this.placementRepository = placementRepository;
        this.specializationRepository = specializationRepository;
        this.domainRepository = domainRepository;
        this.placementFilterRepository = placementFilterRepository;
    }



    @Transactional
    public PlacementFilter savePlacementAndFilter(PlacementRequest request) {
        // Fetch all Specialization and Domain objects based on their IDs
        List<Specialization> specializations = request.specialization_id() == null || request.specialization_id().isEmpty()
                ? null
                : specializationRepository.findAllById(request.specialization_id());

        List<Domain> domains = request.domain_id() == null || request.domain_id().isEmpty()
                ? null
                : domainRepository.findAllById(request.domain_id());

        // Create the Placement object
        Placement placement = new Placement();
        placement.setOrganization(request.organization());
        placement.setProfile(request.profile());
        placement.setDescription(request.description());
        placement.setIntake(request.intake());
        placement.setMinimum_grade(request.minimumGrade());

        // Save the Placement entity and get the saved instance
        Placement savedPlacement = placementRepository.save(placement);

        // Handle case where either specialization or domain is null
        if (specializations == null || domains == null) {
            // Handle the case where one or both lists are null
            PlacementFilter placementFilter = new PlacementFilter();
            placementFilter.setPlacement(savedPlacement);
            placementFilter.setSpecialization(specializations != null && !specializations.isEmpty() ? specializations.get(0) : null); // Set null if no specializations
            placementFilter.setDomain(domains != null && !domains.isEmpty() ? domains.get(0) : null); // Set null if no domains

            // Save the PlacementFilter object
            placementFilterRepository.save(placementFilter);
        } else {
            // Iterate over combinations of Specializations and Domains and create PlacementFilters
            for (Specialization specialization : specializations) {
                for (Domain domain : domains) {
                    // Create the PlacementFilter object for each combination
                    PlacementFilter placementFilter = new PlacementFilter();
                    placementFilter.setPlacement(savedPlacement);
                    placementFilter.setSpecialization(specialization);
                    placementFilter.setDomain(domain);

                    // Save each PlacementFilter object
                    placementFilterRepository.save(placementFilter);
                }
            }
        }

        // Return the last saved PlacementFilter (if needed)
        return placementFilterRepository.findAll().get(placementFilterRepository.findAll().size() - 1);
    }

    public List<Placement> getOffers(){
        return placementRepository.findAll();
    }

    public List<SpecializationResponse> getspecializations() {
        return specializationRepository.findAll().stream()
                .map(specialization -> new SpecializationResponse(
                        specialization.getSpecialization_id(),
                        specialization.getCode(),
                        specialization.getName()))
                .collect(Collectors.toList());
    }

    public List<DomainResponse> getDomains() {
        return domainRepository.findAll()
                .stream()
                .map(domain -> new DomainResponse(
                        domain.getDomain_Id(),
                        domain.getProgram(),
                        domain.getBatch()))
                .collect(Collectors.toList());
    }

//    @Autowired
//    AuthenticationManager authManager;
//
//    @Autowired
//    JWTService jwtService;

//    @Autowired
//    EmployeeRepository employeeRepository;

//    public String verify(Employee employee) {
//
//        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(employee.getUsername())  ;
//        } else {
//            return "fail";
//        }
//    }
}


