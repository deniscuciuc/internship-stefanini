package com.stefanini.pizzariabackend.repo;

import com.stefanini.pizzariabackend.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}