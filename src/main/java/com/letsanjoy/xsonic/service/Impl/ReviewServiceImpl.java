package com.letsanjoy.xsonic.service.Impl;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.domain.Review;
import com.letsanjoy.xsonic.exception.ApiRequestException;
import com.letsanjoy.xsonic.repository.ProductRepository;
import com.letsanjoy.xsonic.repository.ReviewRepository;
import com.letsanjoy.xsonic.service.ReviewService;
import com.letsanjoy.xsonic.constants.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        return product.getReviews();
    }

    @Override
    @Transactional
    public Review addReviewToProduct(Review review, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        List<Review> reviews = product.getReviews();
        reviews.add(review);
        double totalReviews = reviews.size();
        double sumRating = reviews.stream().mapToInt(Review::getRating).sum();
        product.setProductRating(sumRating / totalReviews);
        return reviewRepository.save(review);
    }
}
