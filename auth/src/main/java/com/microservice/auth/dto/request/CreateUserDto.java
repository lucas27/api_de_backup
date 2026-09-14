package com.microservice.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
    @NotBlank(message = "Campo nome não pode estar vázio")
    String name,
    
    @NotBlank(message = "Campo email não pode estar vázio")
    String email,
    
    @NotBlank(message = "Campo senha não pode estar vázio")
    String password
) {} 
