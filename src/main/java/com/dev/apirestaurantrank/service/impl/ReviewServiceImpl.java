package com.dev.apirestaurantrank.service.impl;

import com.dev.apirestaurantrank.dto.ReviewRequest;
import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.dto.ReviewUpdate;
import com.dev.apirestaurantrank.exception.ResourceNotFoundException;
import com.dev.apirestaurantrank.mapper.ReviewMapper;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import com.dev.apirestaurantrank.model.ReviewEntity;
import com.dev.apirestaurantrank.repository.RestaurantRepository;
import com.dev.apirestaurantrank.repository.ReviewRepository;
import com.dev.apirestaurantrank.repository.UserRepository;
import com.dev.apirestaurantrank.service.NotifyRestaurantService;
import com.dev.apirestaurantrank.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final NotifyRestaurantService notifyRestaurantService;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             RestaurantRepository restaurantRepository,
                             UserRepository userRepository,
                             NotifyRestaurantService notifyRestaurantService,
                             ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.notifyRestaurantService = notifyRestaurantService;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public void createReview(ReviewRequest reviewRequest, Long restaurantId, Long userId) {
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant não encontrado"));

        var author = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        ReviewEntity reviewEntity = reviewMapper.toReviewEntity(reviewRequest, restaurant, author);
        restaurant.getReviews().add(reviewEntity);
        reviewRepository.save(reviewEntity);
        notifyRestaurantService.notifyObservers(restaurant, "averageTagStrategy");
    }

    @Override
    public Page<ReviewResponse> getReviews(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<ReviewEntity> reviewPage = reviewRepository.findAllByOrderByRestaurantId_TagAsc(pageable);
        return reviewMapper.toReviewResponsePage(reviewPage);
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review não encontrado"));
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public Page<ReviewResponse> getReviewsByRestaurantId(Long restaurantId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        if (restaurantId == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        Page<ReviewEntity> reviewPage = reviewRepository.findByRestaurantId_IdOrderByRestaurantId_TagAsc(restaurantId, pageable);
        return reviewMapper.toReviewResponsePage(reviewPage);
    }

    @Override
    public Page<ReviewResponse> getReviewsByUserId(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        if (userId == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        Page<ReviewEntity> reviewPage = reviewRepository.findByUserId_IdOrderByRestaurantId_TagAsc(userId, pageable);
        return reviewMapper.toReviewResponsePage(reviewPage);
    }


    @Override
    public void deleteReview(Long reviewId) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review não encontrado"));

        RestaurantEntity restaurant = review.getRestaurant();
        restaurant.getReviews().remove(review);
        reviewRepository.delete(review);
        notifyRestaurantService.notifyObservers(restaurant, "averageTagStrategy");
    }

    @Override
    public void updateReview(Long reviewId, ReviewUpdate reviewUpdate) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review não encontrado"));

        reviewUpdate.rating().ifPresent(newRating -> {
            if (newRating < 1 || newRating > 11) {
                throw new ResourceNotFoundException("a avaliação deve estar entre 1 e 10");
            }
            review.setRating(newRating);
        });
        reviewUpdate.reviewText().ifPresent(review::setReviewText);
        reviewRepository.save(review);
        RestaurantEntity restaurant = review.getRestaurant();
        notifyRestaurantService.notifyObservers(restaurant, "averageTagStrategy");
    }

}
