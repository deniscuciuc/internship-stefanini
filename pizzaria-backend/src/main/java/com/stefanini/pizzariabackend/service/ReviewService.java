package com.stefanini.pizzariabackend.service;

import com.stefanini.pizzariabackend.domain.Review;

import java.util.List;

public interface ReviewService {

    Review saveReview(Review review);

    List<Review> findAllReviews();

    Long deleteReviewById(Long id);
}
