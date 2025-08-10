package com.dev.apirestaurantrank.controller;

import com.dev.apirestaurantrank.dto.RestaurantRequest;
import com.dev.apirestaurantrank.dto.RestaurantResponse;
import com.dev.apirestaurantrank.dto.RestaurantUpdate;
import com.dev.apirestaurantrank.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<RestaurantResponse>> getAllRestaurants(@PathVariable Pageable page) {
        return ResponseEntity.ok(restaurantService.getRestaurants(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        restaurantService.createRestaurant(restaurantRequest);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantUpdate restaurantUpdate) {
        restaurantService.updateRestaurant(id, restaurantUpdate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchRestaurant(@PathVariable Long id, @RequestBody RestaurantUpdate restaurantUpdate) {
        restaurantService.updateRestaurant(id, restaurantUpdate);
        return ResponseEntity.noContent().build();
    }
}
