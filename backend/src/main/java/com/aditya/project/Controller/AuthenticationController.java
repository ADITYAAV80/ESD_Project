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
@CrossOrigin(origins = "http://localhost:5173") //allow requests from 5173
public class AuthenticationController {

    private final OfferService offerService; //linking from service layer

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            // This will throw an exception if login fails
            String token = offerService.loginCustomer(request);

            // Return the token if successful along with 200 code
            return ResponseEntity.ok(token);

        } catch (ResponseStatusException ex) {

            // Return the error message and status and this will trigger login failed message in react
            return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
        }
    }
}