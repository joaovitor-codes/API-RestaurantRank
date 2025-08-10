package com.dev.apirestaurantrank.strategy;

import com.dev.apirestaurantrank.enums.TagEnum;

import java.util.List;

public interface RestaurantTagStrategy {
    TagEnum setTag(List<Integer> ratings);
}
