package com.dev.apirestaurantrank.mapper;

import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.model.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class ReviewMapper {

    public ReviewResponse toReviewResponse(ReviewEntity reviewEntity) {
        return new ReviewResponse(
                reviewEntity.getId(),
                reviewEntity.getRestaurant().getId(),
                reviewEntity.getUser().getId(),
                reviewEntity.getRestaurant().getTag().name(),
                reviewEntity.getRating(),
                reviewEntity.getReviewText());
    }

    public Page<ReviewResponse> toReviewResponsePage(Page<ReviewEntity> reviewPage) {
        return reviewPage.map(this::toReviewResponse);
    }
}
