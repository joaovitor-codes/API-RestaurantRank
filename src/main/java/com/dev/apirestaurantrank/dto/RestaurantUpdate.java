package com.dev.apirestaurantrank.dto;

import java.util.Optional;

public record RestaurantUpdate(
        Optional<String> name,
        Optional<String> address
) {
}
