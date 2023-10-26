package com.letsanjoy.xsonic.service.Impl;

import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.domain.Review;
import com.letsanjoy.xsonic.repository.ProductRepository;
import com.letsanjoy.xsonic.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ReviewServiceImplTest {

    @Autowired
    private ReviewServiceImpl reviewService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    public void addReviewToProduct() {
        List<Review> reviewList = new ArrayList<>();
        Review review = new Review();
        review.setRating(5);
        reviewList.add(review);
        Product product = new Product();
        product.setId(123L);
        product.setReviews(reviewList);

        when(productRepository.findById(123L)).thenReturn(Optional.of(product));
        when(reviewRepository.save(review)).thenReturn(review);
        reviewService.addReviewToProduct(review, 123L);
        assertEquals(123L, product.getId());
        assertNotNull(product.getReviews());
        verify(productRepository, times(1)).findById(123L);
        verify(reviewRepository, times(1)).save(review);
    }
}
