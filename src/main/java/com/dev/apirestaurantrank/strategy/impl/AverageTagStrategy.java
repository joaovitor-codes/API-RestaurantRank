package com.dev.apirestaurantrank.strategy.impl;

import com.dev.apirestaurantrank.enums.TagEnum;
import com.dev.apirestaurantrank.strategy.RestaurantTagStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("averageTagStrategy")
public class AverageTagStrategy implements RestaurantTagStrategy {
    @Override
    public TagEnum setTag(List<Integer> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return TagEnum.UNDEFINED;
        }

        double everage = ratings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);


        if (everage == 0.0) {
            return TagEnum.UNDEFINED;
        } else if (everage <= 2.0) {
            return TagEnum.VARY_BAD;
        } else if (everage >= 3.0 && everage < 6) {
            return TagEnum.BAD;
        } else if (everage >= 6 && everage < 9) {
            return TagEnum.GOOD;
        } else{
            return TagEnum.VERY_GOOD;
        }
    }
}
