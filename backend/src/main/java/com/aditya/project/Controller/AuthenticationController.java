package com.aditya.project.Controller;

import com.aditya.project.DTO.LoginRequest;
import com.aditya.project.Service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
    private final OfferService offerService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String token = offerService.loginCustomer(request);  // This will throw an exception if login fails
            return ResponseEntity.ok(token);  // Return the token if successful
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());  // Return the error message and status
        }
    }
}