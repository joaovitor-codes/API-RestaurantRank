package com.dev.apirestaurantrank.controller;

import com.dev.apirestaurantrank.dto.ReviewRequest;
import com.dev.apirestaurantrank.dto.ReviewResponse;
import com.dev.apirestaurantrank.dto.ReviewUpdate;
import com.dev.apirestaurantrank.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ReviewResponse>> getAllReviews(@PathVariable int page) {
        return ResponseEntity.ok(reviewService.getReviews(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByRestaurantId(@PathVariable Long restaurantId, int page) {
        return ResponseEntity.ok(reviewService.getReviewsByRestaurantId(restaurantId, page));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByUserId(@PathVariable Long userId, int page) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId, page));
    }

    @PostMapping("{userId}/{restaurantId}")
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequest reviewRequest, @PathVariable Long userId, @PathVariable Long restaurantId) {
        reviewService.createReview(reviewRequest, restaurantId, userId);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable Long id, @RequestBody ReviewUpdate reviewUpdate) {
        reviewService.updateReview(id, reviewUpdate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchReview(@PathVariable Long id, @RequestBody ReviewUpdate reviewUpdate) {
        reviewService.updateReview(id, reviewUpdate);
        return ResponseEntity.noContent().build();
    }
}
