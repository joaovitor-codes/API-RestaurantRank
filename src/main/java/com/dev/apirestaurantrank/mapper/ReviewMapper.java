package com.dev.apirestaurantrank.mapper;

import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.model.ReviewEntity;
import org.springframework.data.domain.Page;

public interface ReviewMapper {
    ReviewResponse toReviewResponse(ReviewEntity review);
    Page<ReviewResponse> toReviewResponsePage(Page<ReviewEntity> reviewPage);
}
