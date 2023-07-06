package com.stefanini.pizzariabackend.repo;

import com.stefanini.pizzariabackend.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}