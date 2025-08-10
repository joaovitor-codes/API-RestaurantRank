package com.dev.apirestaurantrank.service;

import com.dev.apirestaurantrank.dto.ReviewRequest;
import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.dto.ReviewUpdate;
import com.dev.apirestaurantrank.exception.ResourceNotFoundException;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import com.dev.apirestaurantrank.model.ReviewEntity;
import com.dev.apirestaurantrank.observer.TagUpdaterObserver;
import com.dev.apirestaurantrank.repository.RestaurantRepository;
import com.dev.apirestaurantrank.repository.ReviewRepository;
import com.dev.apirestaurantrank.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final TagUpdaterObserver tagUpdaterObserver;

    public ReviewService(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, TagUpdaterObserver tagUpdaterObserver) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.tagUpdaterObserver = tagUpdaterObserver;
    }

    private Page<ReviewResponse> mapToReviewResponsePage(Page<ReviewEntity> reviewPage, Pageable pageable) {
        List<ReviewResponse> reviewResponses = reviewPage.getContent().stream()
                .map(review -> new ReviewResponse(
                        review.getId(),
                        review.getRestaurantId().getId(),
                        review.getUserId().getId(),
                        review.getRestaurantId().getTag().name(),
                        review.getRating(),
                        review.getReviewText()))
                .collect(Collectors.toList());

        return new PageImpl<>(reviewResponses, pageable, reviewPage.getTotalElements());
    }

    public void createReview(ReviewRequest reviewRequest, Long restaurantId, Long userId) {
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant não encontrado"));

        var author = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setRestaurantId(restaurant);
        reviewEntity.setUserId(author);
        reviewEntity.setRating(reviewRequest.rating());
        if (reviewRequest.reviewText().isPresent()) {
            reviewRequest.reviewText().ifPresent(reviewEntity::setReviewText);
        } else {
            reviewEntity.setReviewText("Sem comentário");
        }
        restaurant.getReviews().add(reviewEntity);
        reviewRepository.save(reviewEntity);

        tagUpdaterObserver.setStrategyName("averageTagStrategy");
        restaurant.registerObserver(tagUpdaterObserver);
        restaurant.notifyObservers();
        restaurant.removeObserver(tagUpdaterObserver);
    }

    public Page<ReviewResponse> getReviews(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<ReviewEntity> reviewPage = reviewRepository.findAllByOrderByRestaurantId_TagAsc(pageable);
        return mapToReviewResponsePage(reviewPage, pageable);
    }

    public ReviewResponse getReviewById(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review não encontrado"));

        return new ReviewResponse(
                review.getId(),
                review.getRestaurantId().getId(),
                review.getUserId().getId(),
                review.getRestaurantId().getTag().name(),
                review.getRating(),
                review.getReviewText()
        );
    }

    public Page<ReviewResponse> getReviewsByRestaurantId(Long restaurantId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        if (restaurantId == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        Page<ReviewEntity> reviewPage = reviewRepository.findByRestaurantId_IdOrderByRestaurantId_TagAsc(restaurantId, pageable);
        return mapToReviewResponsePage(reviewPage, pageable);
    }

    public Page<ReviewResponse> getReviewsByUserId(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        if (userId == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        Page<ReviewEntity> reviewPage = reviewRepository.findByUserId_IdOrderByRestaurantId_TagAsc(userId, pageable);
        return mapToReviewResponsePage(reviewPage, pageable);
    }


    public void deleteReview(Long reviewId) {
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review não encontrado"));

        RestaurantEntity restaurant = review.getRestaurantId();
        restaurant.getReviews().remove(review);
        reviewRepository.delete(review);

        tagUpdaterObserver.setStrategyName("averageStrategy");
        restaurant.registerObserver(tagUpdaterObserver);
        restaurant.notifyObservers();
        restaurant.removeObserver(tagUpdaterObserver);
    }

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

        RestaurantEntity restaurant = review.getRestaurantId();
        tagUpdaterObserver.setStrategyName("averageStrategy");
        restaurant.registerObserver(tagUpdaterObserver);
        restaurant.notifyObservers();
        restaurant.removeObserver(tagUpdaterObserver);
    }

}
