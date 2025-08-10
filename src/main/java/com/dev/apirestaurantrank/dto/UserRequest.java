package com.dev.apirestaurantrank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
    @NotBlank
    String name,
    @NotBlank
    @Email(message = "O e-mail deve ter um formato válido.")
    String email,
    @NotBlank
    String password
) {
}
