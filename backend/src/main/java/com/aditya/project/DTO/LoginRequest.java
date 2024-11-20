package com.aditya.project.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Username is required")
        @JsonProperty("username")
        String username,

        @NotBlank(message = "Password is required")
        @JsonProperty("password")
        String password
) {
}