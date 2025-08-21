package com.dev.apirestaurantrank.model;

import com.dev.apirestaurantrank.enums.TagEnum;
import com.dev.apirestaurantrank.observer.Observer;
import com.dev.apirestaurantrank.observer.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity implements Subject<RestaurantEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private TagEnum tag;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews;

    @Transient
    private Set<Observer<RestaurantEntity>> observers = new HashSet<>();

    @Override
    public void registerObserver(Observer<RestaurantEntity> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<RestaurantEntity> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<RestaurantEntity> observer : observers) {
            observer.update(this);
        }
    }
}
