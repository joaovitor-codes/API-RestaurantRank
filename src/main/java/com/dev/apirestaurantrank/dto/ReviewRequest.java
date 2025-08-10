package com.dev.apirestaurantrank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.Optional;

public record ReviewRequest(
    @NotBlank
    Long restaurantId,
    @NotBlank
    Long userId,
    @NotBlank
    @Positive
    double rating,
    Optional<String> reviewText
) {
}
