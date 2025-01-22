package com.jeeorg.reviewms.review.impl;


import com.jeeorg.reviewms.review.Review;
import com.jeeorg.reviewms.review.ReviewRepository;
import com.jeeorg.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review createReview(Long companyId, Review review) {
        if(companyId != null && review != null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return review;
        }
        return null;
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Review updateReview(Long reviewId, Review newReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setTitle(newReview.getTitle());
            review.setDescription(newReview.getDescription());
            review.setRating(newReview.getRating());
            review.setCompanyId(newReview.getCompanyId());
            reviewRepository.save(review);
            return review;
        }
        return null;
    }

    @Override
    public Boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null){
            reviewRepository.delete(review);
            return true;
        } else
            return false;

    }
}
