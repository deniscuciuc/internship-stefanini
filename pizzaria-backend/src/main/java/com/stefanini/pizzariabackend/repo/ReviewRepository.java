package com.stefanini.pizzariabackend.repo;

import com.stefanini.pizzariabackend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
