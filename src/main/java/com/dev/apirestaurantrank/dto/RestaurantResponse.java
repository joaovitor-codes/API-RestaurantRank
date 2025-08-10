package com.dev.apirestaurantrank.dto;

public record RestaurantResponse(
        Long id,
        String tag,
        String name,
        String address
) {
}
