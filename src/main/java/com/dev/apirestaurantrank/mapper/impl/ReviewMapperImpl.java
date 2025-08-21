package com.dev.apirestaurantrank.mapper.impl;

import com.dev.apirestaurantrank.dto.ReviewRequest;
import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.mapper.ReviewMapper;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import com.dev.apirestaurantrank.model.ReviewEntity;
import com.dev.apirestaurantrank.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapperImpl implements ReviewMapper {
    public ReviewResponse toReviewResponse(ReviewEntity review) {
        if (review == null) {
            return null;
        }

        return new ReviewResponse(
                review.getId(),
                review.getRestaurant().getId(),
                review.getUser().getId(),
                review.getRestaurant().getTag().name(),
                review.getRating(),
                review.getReviewText());
    }

    public ReviewEntity toReviewEntity(ReviewRequest request, RestaurantEntity restaurant, UserEntity author) {
        return ReviewEntity.builder()
                .rating(request.rating())
                .reviewText(request.reviewText().orElse("Sem comentário"))
                .restaurant(restaurant)
                .user(author)
                .build();
    }

    public Page<ReviewResponse> toReviewResponsePage(Page<ReviewEntity> reviewPage) {
        if (reviewPage == null) {
            return Page.empty();
        }

        List<ReviewResponse> reviewResponses = reviewPage
                .getContent()
                .stream()
                .map(this::toReviewResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(reviewResponses, reviewPage.getPageable(), reviewPage.getTotalElements());
    }
}
