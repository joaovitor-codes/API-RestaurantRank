package com.dev.apirestaurantrank.service;

import com.dev.apirestaurantrank.dto.RestaurantRequest;
import com.dev.apirestaurantrank.dto.RestaurantResponse;
import com.dev.apirestaurantrank.dto.RestaurantUpdate;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    Page<RestaurantResponse> mapToRestaurantResponsePage(Page<RestaurantEntity> restaurantEntityPage, Pageable pageable);
    Page<RestaurantResponse> getRestaurants(int page);
    RestaurantResponse getRestaurantById(Long id);
    void createRestaurant(RestaurantRequest restaurantRequest);
    void updateRestaurant(Long id, RestaurantUpdate restaurantUpdate);
    void deleteRestaurant(Long id);
}
