package com.dev.apirestaurantrank.dto;

import jakarta.validation.constraints.NotBlank;

public record RestaurantRequest(
        @NotBlank
        String name,
        @NotBlank
        String address
) {
}
