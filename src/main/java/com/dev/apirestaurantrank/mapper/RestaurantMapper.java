package com.dev.apirestaurantrank.mapper;

import com.dev.apirestaurantrank.dto.RestaurantResponse;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import org.springframework.data.domain.Page;

public interface RestaurantMapper {
    RestaurantResponse toRestaurantResponse(RestaurantEntity restaurant);
    Page<RestaurantResponse> toRestaurantResponsePage(Page<RestaurantEntity> restaurants);
}
