package com.letsanjoy.xsonic.controller;

import com.letsanjoy.xsonic.dto.review.ReviewRequest;
import com.letsanjoy.xsonic.dto.review.ReviewResponse;
import com.letsanjoy.xsonic.mapper.ReviewMapper;
import com.letsanjoy.xsonic.constants.PathConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_REVIEW)
public class ReviewController {

    private final ReviewMapper reviewMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping(PathConstants.PRODUCT_ID)
    public ResponseEntity<List<ReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewMapper.getReviewsByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReviewToProduct(@Valid @RequestBody ReviewRequest reviewRequest,
                                                             BindingResult bindingResult) {
        ReviewResponse review = reviewMapper.addReviewToProduct(reviewRequest, reviewRequest.getProductId(), bindingResult);
        messagingTemplate.convertAndSend("/topic/reviews/" + reviewRequest.getProductId(), review);
        return ResponseEntity.ok(review);
    }
}
