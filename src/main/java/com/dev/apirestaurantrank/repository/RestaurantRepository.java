package com.dev.apirestaurantrank.repository;

import com.dev.apirestaurantrank.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
