package com.dev.apirestaurantrank.dto;

public record ReviewResponse(
    Long id,
    Long restaurantId,
    Long userId,
    String tagName,
    double rating,
    String reviewText
) {
}
