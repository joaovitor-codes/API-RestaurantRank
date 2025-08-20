package com.dev.apirestaurantrank.service;

import com.dev.apirestaurantrank.dto.ReviewRequest;
import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.dto.ReviewUpdate;
import com.dev.apirestaurantrank.model.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ReviewResponse> mapToReviewResponsePage(Page<ReviewEntity> reviewPage, Pageable pageable);
    void createReview(ReviewRequest reviewRequest, Long restaurantId, Long userId);
    Page<ReviewResponse> getReviews(int page);
    ReviewResponse getReviewById(Long id);
    Page<ReviewResponse> getReviewsByRestaurantId(Long restaurantId, int page);
    Page<ReviewResponse> getReviewsByUserId(Long userId, int page);
    void deleteReview(Long reviewId);
    void updateReview(Long reviewId, ReviewUpdate reviewUpdate);
}
