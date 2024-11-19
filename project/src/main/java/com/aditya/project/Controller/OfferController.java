package com.aditya.project.Controller;

import com.aditya.project.DTO.PlacementRequest;
import com.aditya.project.Model.Employee;
import com.aditya.project.Model.Placement;
import com.aditya.project.Model.PlacementFilter;
import com.aditya.project.Repository.PlacementRepository;
import com.aditya.project.Service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OfferController {

    public final OfferService service; // Make the field final to enforce proper initialization.

    @Autowired
    public OfferController(OfferService service) {
        this.service = service; // Correctly assign the parameter to the field.
    }

    @PostMapping("/submit")
    public String getOffers(@RequestBody PlacementRequest request){
        PlacementFilter savedPlacement = service.savePlacementAndFilter(request);
        return "Placement saved with ID: " + savedPlacement.getId();
    }

    @PostMapping("/login")
    public String login(@RequestBody Employee employee){
        return service.verify(employee);
    }



    @GetMapping("/")
    public List<Placement> showOffers() {
        return service.getOffers(); // Accessing the properly initialized service.
    }

}
