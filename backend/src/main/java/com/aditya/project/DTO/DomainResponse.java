package com.aditya.project.DTO;

//DTO is like object we transfer between layers and to and from client for standardized data

import com.fasterxml.jackson.annotation.JsonProperty;

public record DomainResponse (
        @JsonProperty("domain_id")
        Integer domainId,

        @JsonProperty("program")
        String program,

        @JsonProperty("batch")
        String batch
){}
