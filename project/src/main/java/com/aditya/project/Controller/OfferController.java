package com.aditya.project.Controller;

import com.aditya.project.DTO.DomainResponse;
import com.aditya.project.DTO.LoginRequest;
import com.aditya.project.DTO.PlacementRequest;
import com.aditya.project.DTO.SpecializationResponse;
import com.aditya.project.Model.*;
import com.aditya.project.Service.OfferService;
import com.aditya.project.helper.JWTHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class OfferController {

    public final OfferService service; // Make the field final to enforce proper initialization.
    public final JWTHelper jwtHelper;

    @Autowired
    public OfferController(OfferService service,JWTHelper jwtHelper) {
        this.service = service; // Correctly assign the parameter to the field.
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> getOffers(@RequestBody PlacementRequest request, HttpServletRequest req){

        if(!jwtHelper.validateAuthorizationHeader(req.getHeader("Authorization"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        PlacementFilter savedPlacement = service.savePlacementAndFilter(request);
        return ResponseEntity.ok("Placement saved with ID: " + savedPlacement.getId());
    }

    @GetMapping("/api/specialization")
    public ResponseEntity<List<SpecializationResponse>> getSpecializations(HttpServletRequest req) {
        if(!jwtHelper.validateAuthorizationHeader(req.getHeader("Authorization"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(service.getspecializations());
    }

    @GetMapping("api/domain")
        public ResponseEntity<List<DomainResponse>> getDomains(HttpServletRequest req) {
        if(!jwtHelper.validateAuthorizationHeader(req.getHeader("Authorization"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(service.getDomains());
    }


    @GetMapping("/")
    public ResponseEntity<List<Placement>> showOffers(HttpServletRequest req) {
        if(!jwtHelper.validateAuthorizationHeader(req.getHeader("Authorization"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(service.getOffers()); // Accessing the properly initialized service.
    }


}
