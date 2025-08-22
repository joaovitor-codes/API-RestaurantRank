package com.dev.apirestaurantrank.service;

import com.dev.apirestaurantrank.model.RestaurantEntity;

public interface NotifyRestaurantService {
    void notifyObservers(RestaurantEntity restaurant, String strategyName);
}
