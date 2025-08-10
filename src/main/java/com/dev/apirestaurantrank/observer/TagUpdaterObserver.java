package com.dev.apirestaurantrank.observer;

import com.dev.apirestaurantrank.enums.TagEnum;
import com.dev.apirestaurantrank.model.RestaurantEntity;
import com.dev.apirestaurantrank.repository.RestaurantRepository;
import com.dev.apirestaurantrank.strategy.RestaurantTagStrategy;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Setter
public class TagUpdaterObserver implements Observer<RestaurantEntity> {
    private final RestaurantRepository restaurantRepository;
    private final Map<String, RestaurantTagStrategy> restaurantTagStrategy;
    private String strategyName;

    public TagUpdaterObserver(RestaurantRepository restaurantRepository,
                              Map<String, RestaurantTagStrategy> restaurantTagStrategy) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantTagStrategy = restaurantTagStrategy;
    }

    @Override
    public void update(RestaurantEntity restaurant) {
        if (strategyName == null || !restaurantTagStrategy.containsKey(this.strategyName)) {
            this.strategyName = "averageTagStrategy";
        }

        RestaurantTagStrategy strategy = restaurantTagStrategy.get(this.strategyName);

        List<Integer> ratings = restaurant.getReviews()
                .stream()
                .map(review -> (int) review.getRating())
                .collect(Collectors.toList());

        TagEnum newTag = strategy.setTag(ratings);
        restaurant.setTag(newTag);
        restaurantRepository.save(restaurant);
    }
}
