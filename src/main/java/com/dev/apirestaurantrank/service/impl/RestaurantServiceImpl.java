package com.dev.apirestaurantrank.service.impl;

import com.dev.apirestaurantrank.dto.RestaurantRequest;
import com.dev.apirestaurantrank.dto.RestaurantResponse;
import com.dev.apirestaurantrank.dto.RestaurantUpdate;
import com.dev.apirestaurantrank.exception.ResourceNotFoundException;
import com.dev.apirestaurantrank.mapper.RestaurantMapper;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import com.dev.apirestaurantrank.repository.RestaurantRepository;
import com.dev.apirestaurantrank.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    public Page<RestaurantResponse> getRestaurants(int page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        Page<RestaurantEntity> restaurantEntityPage = restaurantRepository.findAll(pageable);
        return restaurantMapper.toRestaurantResponsePage(restaurantEntityPage);
    }

    public RestaurantResponse getRestaurantById(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
        return restaurantMapper.toRestaurantResponse(restaurant);
    }

    public void createRestaurant(RestaurantRequest restaurantRequest) {
        RestaurantEntity restaurantEntity = restaurantMapper.toRestaurantEntity(restaurantRequest);
        restaurantRepository.save(restaurantEntity);
    }

    public void updateRestaurant(Long id, RestaurantUpdate restaurantUpdate) {
        RestaurantEntity restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        restaurantUpdate.name().ifPresent(restaurant::setName);
        restaurantUpdate.address().ifPresent(restaurant::setAddress);

        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        restaurantRepository.delete(restaurant);
    }
}