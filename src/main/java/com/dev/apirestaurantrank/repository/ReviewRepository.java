package com.dev.apirestaurantrank.repository;

import com.dev.apirestaurantrank.model.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Page<ReviewEntity> findAll(Pageable pageable);
    Page<ReviewEntity> findAllByOrderByRestaurantId_TagAsc(Pageable pageable);
    Page<ReviewEntity> findByRestaurantId_IdOrderByRestaurantId_TagAsc(Long restaurantId, Pageable pageable);
    Page<ReviewEntity> findByUserId_IdOrderByRestaurantId_TagAsc(Long userId, Pageable pageable);
}
