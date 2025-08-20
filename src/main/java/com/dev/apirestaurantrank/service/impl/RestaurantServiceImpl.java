package com.dev.apirestaurantrank.service.impl;

import com.dev.apirestaurantrank.dto.RestaurantRequest;
import com.dev.apirestaurantrank.dto.RestaurantResponse;
import com.dev.apirestaurantrank.dto.RestaurantUpdate;
import com.dev.apirestaurantrank.enums.TagEnum;
import com.dev.apirestaurantrank.exception.ResourceNotFoundException;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import com.dev.apirestaurantrank.repository.RestaurantRepository;
import com.dev.apirestaurantrank.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Page<RestaurantResponse> mapToRestaurantResponsePage(Page<RestaurantEntity> restaurantEntityPage, Pageable pageable) {
        List<RestaurantResponse> restaurantResponses = restaurantEntityPage.getContent().stream()
                .map(restaurant -> new RestaurantResponse(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getAddress(),
                        restaurant.getTag().name()
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(restaurantResponses, pageable, restaurantEntityPage.getTotalElements());
    }

    public Page<RestaurantResponse> getRestaurants(int page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        Page<RestaurantEntity> restaurantEntityPage = restaurantRepository.findAll(pageable);
        return mapToRestaurantResponsePage(restaurantEntityPage, pageable);
    }

    public RestaurantResponse getRestaurantById(Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));

        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getTag().name()
        );
    }

    public void createRestaurant(RestaurantRequest restaurantRequest) {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName(restaurantRequest.name());
        restaurantEntity.setAddress(restaurantRequest.address());
        restaurantEntity.setTag(TagEnum.UNDEFINED);
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurantEntity);

        new RestaurantResponse(
                savedRestaurant.getId(),
                savedRestaurant.getName(),
                savedRestaurant.getAddress(),
                savedRestaurant.getTag().name()
        );
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