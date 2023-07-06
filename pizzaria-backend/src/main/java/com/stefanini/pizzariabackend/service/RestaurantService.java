package com.stefanini.pizzariabackend.service;

import com.stefanini.pizzariabackend.domain.Restaurant;

import java.util.List;

/**
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface RestaurantService {

    /**
     * Saves restaurant.
     *
     * @param restaurant to be saved
     * @return saved restaurant
     */
    Restaurant saveRestaurant(Restaurant restaurant);

    /**
     * Finds all restaurants.
     *
     * @return all restaurants or empty list
     */
    List<Restaurant> findAllRestaurants();

    /**
     * Delete restaurant by its id.
     *
     * @param id of restaurant to be deleted
     * @return id of deleted restaurant
     */
    Long deleteRestaurantById(Long id);
}
