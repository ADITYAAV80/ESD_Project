package com.aditya.project.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;
import java.util.Optional;

public record PlacementRequest (

        @NotNull(message = "Organization cannot be null")
        @NotBlank(message = "Organization cannot be blank")
        @JsonProperty("organization")
        String organization,

        @NotNull(message = "Profile cannot be null")
        @NotBlank(message = "Profile cannot be blank")
        @JsonProperty("profile")
        String profile,

        @NotNull(message = "Description cannot be null")
        @NotBlank(message = "Description cannot be blank")
        @JsonProperty("description")
        String description,


        @JsonProperty("intake")
        Integer intake,

        @JsonProperty("minimumGrade")
        Float minimumGrade,

        @JsonProperty("specialization_id")
        List<Integer> specialization_id,  // Changed to List<Integer> to handle multiple specialization IDs

        @JsonProperty("domain_id")
        List<Integer> domain_id  // Changed to List<Integer> to handle multiple domain IDs
) {}
