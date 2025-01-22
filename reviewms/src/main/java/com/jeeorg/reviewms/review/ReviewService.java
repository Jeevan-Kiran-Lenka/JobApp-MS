package com.jeeorg.reviewms.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);
    Review createReview(Long companyId, Review review);
    Review getReview(Long reviewId);
    Review updateReview(Long reviewId, Review newReview);
    Boolean deleteReview(Long reviewId);
}
