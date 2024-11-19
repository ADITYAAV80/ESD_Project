package com.aditya.project.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DomainResponse (
        @JsonProperty("domain_id")
        Integer domainId,

        @JsonProperty("program")
        String program,

        @JsonProperty("batch")
        String batch
){}
