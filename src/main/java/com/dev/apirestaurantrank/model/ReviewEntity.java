package com.dev.apirestaurantrank.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double rating;
    private String reviewText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId", referencedColumnName = "id")
    private RestaurantEntity restaurant;
}
