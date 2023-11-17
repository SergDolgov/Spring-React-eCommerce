package com.letsanjoy.xsonic.service;

import com.letsanjoy.xsonic.domain.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByProductId(Long productId);

    Review addReviewToProduct(Review review, Long productId);
}
