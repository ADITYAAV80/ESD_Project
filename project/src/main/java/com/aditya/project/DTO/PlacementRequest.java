package com.aditya.project.DTO;

import com.aditya.project.Model.Domain;
import com.aditya.project.Model.Placement;
import com.aditya.project.Model.Specialization;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    Integer minimumGrade,


    @JsonProperty("specialization_id")
    Integer specialization_id,


    @JsonProperty("domain_id")
    Integer domain_id
){}


