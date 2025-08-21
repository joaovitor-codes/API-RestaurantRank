package com.dev.apirestaurantrank.mapper.impl;


import com.dev.apirestaurantrank.dto.RestaurantRequest;
import com.dev.apirestaurantrank.dto.RestaurantResponse;
import com.dev.apirestaurantrank.mapper.RestaurantMapper;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import com.dev.apirestaurantrank.enums.TagEnum;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapperImpl implements RestaurantMapper {
    public RestaurantResponse toRestaurantResponse(RestaurantEntity restaurant) {
        if (restaurant == null) {
            return null;
        }

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getTag().name(),
                restaurant.getName(),
                restaurant.getAddress());
    }

    public RestaurantEntity toRestaurantEntity(RestaurantRequest request) {
        return RestaurantEntity.builder()
                .tag(TagEnum.UNDEFINED)
                .name(request.name())
                .address(request.address())
                .build();
    }

    public Page<RestaurantResponse> toRestaurantResponsePage(Page<RestaurantEntity> restaurants) {
        if (restaurants == null) {
            return null;
        }

        List<RestaurantResponse> restaurantResponses = restaurants.getContent().stream()
                .map(this::toRestaurantResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(restaurantResponses, restaurants.getPageable(), restaurants.getTotalElements());
    }
}
