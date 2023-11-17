package com.letsanjoy.xsonic.mapper;

import com.letsanjoy.xsonic.domain.Review;
import com.letsanjoy.xsonic.dto.review.ReviewRequest;
import com.letsanjoy.xsonic.dto.review.ReviewResponse;
import com.letsanjoy.xsonic.exception.InputFieldException;
import com.letsanjoy.xsonic.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final CommonMapper commonMapper;
    private final ReviewService reviewService;

    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        return commonMapper.convertToResponseList(reviewService.getReviewsByProductId(productId), ReviewResponse.class);
    }

    public ReviewResponse addReviewToProduct(ReviewRequest reviewRequest, Long productId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Review review = commonMapper.convertToEntity(reviewRequest, Review.class);
        return commonMapper.convertToResponse(reviewService.addReviewToProduct(review, productId), ReviewResponse.class);
    }
}
