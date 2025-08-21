package com.dev.apirestaurantrank.mapper;

import com.dev.apirestaurantrank.dto.ReviewRequest;
import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import com.dev.apirestaurantrank.model.ReviewEntity;
import com.dev.apirestaurantrank.model.UserEntity;
import org.springframework.data.domain.Page;

public interface ReviewMapper {
    ReviewResponse toReviewResponse(ReviewEntity review);
    ReviewEntity toReviewEntity(ReviewRequest request, RestaurantEntity restaurant, UserEntity author);
    Page<ReviewResponse> toReviewResponsePage(Page<ReviewEntity> reviewPage);
}
