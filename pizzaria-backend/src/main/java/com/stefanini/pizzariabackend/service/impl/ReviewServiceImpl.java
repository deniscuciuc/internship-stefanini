package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.Review;
import com.stefanini.pizzariabackend.repo.ReviewRepository;
import com.stefanini.pizzariabackend.service.ReviewService;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import com.stefanini.pizzariabackend.service.impl.helper.ValuesChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Long deleteReviewById(Long id) {
        ValuesChecker.verifyIdAndIfInvalidThrowException(id);
        verifyIfReviewExistsById(id);
        reviewRepository.deleteById(id);
        return id;
    }

    private void verifyIfReviewExistsById(Long id) throws NotFoundException {
        boolean doesReviewExist = reviewRepository.existsById(id);
        if (!doesReviewExist) {
            log.error("Review with id {} not found", id);
            throw new NotFoundException("Review with id " + id + " not found");
        }
    }
}
