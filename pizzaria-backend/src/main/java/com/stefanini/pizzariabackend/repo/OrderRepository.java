package com.stefanini.pizzariabackend.repo;

import com.stefanini.pizzariabackend.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}