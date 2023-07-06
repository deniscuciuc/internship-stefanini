package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.Restaurant;
import com.stefanini.pizzariabackend.repo.RestaurantRepository;
import com.stefanini.pizzariabackend.service.RestaurantService;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import com.stefanini.pizzariabackend.service.impl.helper.ValuesChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Long deleteRestaurantById(Long id) {
        ValuesChecker.verifyIdAndIfInvalidThrowException(id);
        verifyIfRestaurantExistsById(id);
        restaurantRepository.deleteById(id);
        return id;
    }

    private void verifyIfRestaurantExistsById(Long id) throws NotFoundException {
        boolean doesRestaurantExist = restaurantRepository.existsById(id);
        if (!doesRestaurantExist) {
            log.error("Restaurant with id {} not found", id);
            throw new NotFoundException("Restaurant with id " + id + " not found");
        }
    }
}
