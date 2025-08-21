package com.dev.apirestaurantrank.service;

import com.dev.apirestaurantrank.dto.ReviewRequest;
import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.dto.ReviewUpdate;
import org.springframework.data.domain.Page;

public interface ReviewService {
    void createReview(ReviewRequest reviewRequest, Long restaurantId, Long userId);
    Page<ReviewResponse> getReviews(int page);
    ReviewResponse getReviewById(Long id);
    Page<ReviewResponse> getReviewsByRestaurantId(Long restaurantId, int page);
    Page<ReviewResponse> getReviewsByUserId(Long userId, int page);
    void deleteReview(Long reviewId);
    void updateReview(Long reviewId, ReviewUpdate reviewUpdate);
}
