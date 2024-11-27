package com.aditya.project.Controller;

import com.aditya.project.DTO.DomainResponse;
import com.aditya.project.DTO.PlacementRequest;
import com.aditya.project.DTO.SpecializationResponse;
import com.aditya.project.Model.*;
import com.aditya.project.Service.OfferService;
import com.aditya.project.Helper.JWTHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController    //request is page + some body we want to send only body of request
@CrossOrigin(origins = "http://localhost:5173") //allow request from foreign sources
public class OfferController {

    public final OfferService service; // Service x Controller // Make the field final to enforce proper initialization. like once initialized you can't change
    public final JWTHelper jwtHelper; //JwtHelper(for user authentication) x Controller


//    public OfferController(OfferService service,JWTHelper jwtHelper) {
//        this.service = service;
//        this.jwtHelper = jwtHelper;
//    } //this is essentially linking since they don't belong in the same class

    @PostMapping("/submit")
    public ResponseEntity<String> getOffers(@Valid @RequestBody PlacementRequest request, HttpServletRequest req){

        // basically you are checking for jwt token if it is valid you are allowing it else you are rejecting it
        if(!jwtHelper.validateAuthorizationHeader(req.getHeader("Authorization"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // you are transferring the load to the service layer because controller is not responsible to handle business logic
        PlacementFilter savedPlacement = service.savePlacementAndFilter(request);

        // returning code 200 along with some string
        return ResponseEntity.ok("Placement saved with ID: " + savedPlacement.getId());
    }

    // you are sending the list of specialization to fill in the form
    @GetMapping("/api/specialization")
    public ResponseEntity<List<SpecializationResponse>> getSpecializations(HttpServletRequest req) {
        if(!jwtHelper.validateAuthorizationHeader(req.getHeader("Authorization"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        //return sending specializations to fill in the form but logic is handled by service layer
        return ResponseEntity.ok(service.getspecializations());
    }

    //you are sending the list of domain to fill in the form
    @GetMapping("api/domain")
        public ResponseEntity<List<DomainResponse>> getDomains(HttpServletRequest req) {
        if(!jwtHelper.validateAuthorizationHeader(req.getHeader("Authorization"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        //return sending domains to fill in the form but logic is handled by service layer
        return ResponseEntity.ok(service.getDomains());
    }


    //home function dummy just to understand initial flow
    @GetMapping("/")
    public ResponseEntity<List<Placement>> showOffers(HttpServletRequest req) {
        if(!jwtHelper.validateAuthorizationHeader(req.getHeader("Authorization"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(service.getOffers()); // Accessing the properly initialized service.
    }


}
