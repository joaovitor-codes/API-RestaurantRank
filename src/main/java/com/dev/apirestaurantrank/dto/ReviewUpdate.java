package com.dev.apirestaurantrank.dto;

import java.util.Optional;

public record ReviewUpdate(
    Optional<Double> rating,
    Optional<String> reviewText
) {
}
