package com.aditya.project.Service;

import com.aditya.project.DTO.DomainResponse;
import com.aditya.project.DTO.LoginRequest;
import com.aditya.project.DTO.PlacementRequest;
import com.aditya.project.DTO.SpecializationResponse;
import com.aditya.project.Encryption.EncryptionService;
import com.aditya.project.Model.*;
import com.aditya.project.Repository.*;
import com.aditya.project.Helper.JWTHelper;
import com.aditya.project.Utility.InvalidCredentialsException;
import com.aditya.project.Utility.NullFieldsException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OfferService {

    private final PlacementRepository placementRepository;
    private final SpecializationRepository specializationRepository;
    private final DomainRepository domainRepository;
    private final PlacementFilterRepository placementFilterRepository;
    private final EmployeeRepository employeeRepository;
    private final EncryptionService encryptionService;
    private final JWTHelper jwtHelper;

//    @Autowired
//    public OfferService(PlacementRepository placementRepository,
//                        SpecializationRepository specializationRepository,
//                        DomainRepository domainRepository,
//                        PlacementFilterRepository placementFilterRepository,
//                        EmployeeRepository employeeRepository,
//                        EncryptionService encryptionService, JWTHelper jwtHelper) {
//        this.placementRepository = placementRepository;
//        this.specializationRepository = specializationRepository;
//        this.domainRepository = domainRepository;
//        this.placementFilterRepository = placementFilterRepository;
//        this.employeeRepository = employeeRepository;
//        this.encryptionService = encryptionService;
//        this.jwtHelper = jwtHelper;
//    }

    @Transactional
    public PlacementFilter savePlacementAndFilter(@Valid PlacementRequest request) {
        // Fetch all Specialization and Domain objects based on their IDs
        if(request.organization()==null|| request.profile()==null|| request.description()==null){
            throw new NullFieldsException("Invalid credentials");
        }


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

    public String loginCustomer(@Valid LoginRequest request) {

        Employee employee = employeeRepository.findByUsername(request.username());
        if (employee == null || !encryptionService.validates(request.password(), employee.getPassword())) {

            throw new InvalidCredentialsException("Incorrect email or password");//Passing to Global Exception
            // Return an error message with 401 status
            //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect email or password");
        }
        return jwtHelper.generateToken(request.username());
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


