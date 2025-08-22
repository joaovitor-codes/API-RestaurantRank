package com.dev.apirestaurantrank.service.impl;

import com.dev.apirestaurantrank.model.RestaurantEntity;
import com.dev.apirestaurantrank.observer.TagUpdaterObserver;
import com.dev.apirestaurantrank.service.NotifyRestaurantService;
import org.springframework.stereotype.Service;

@Service
public class NotifyRestaurantServiceImpl implements NotifyRestaurantService {
    private final TagUpdaterObserver tagUpdaterObserver;

    public NotifyRestaurantServiceImpl(TagUpdaterObserver tagUpdaterObserver) {
        this.tagUpdaterObserver = tagUpdaterObserver;
    }

    @Override
    public void notifyObservers(RestaurantEntity restaurant, String strategyName) {
        tagUpdaterObserver.setStrategyName(strategyName);
        restaurant.registerObserver(tagUpdaterObserver);
        restaurant.notifyObservers();
        restaurant.removeObserver(tagUpdaterObserver);
    }
}
