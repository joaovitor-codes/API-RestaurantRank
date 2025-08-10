package com.dev.apirestaurantrank.repository;

import com.dev.apirestaurantrank.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
