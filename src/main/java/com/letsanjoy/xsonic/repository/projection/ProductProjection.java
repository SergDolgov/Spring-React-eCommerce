package com.letsanjoy.xsonic.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ProductProjection {
    Long getId();
    String getProductTitle();
    String getPoducer();
    Integer getPrice();
    String getFilename();
    Double getProductRating();
    
    @Value("#{target.reviews.size()}")
    Integer getReviewsCount();

    void setPoducer(String poducer);
    void setProductGender(String productGender);
    void setPrice(Integer price);
}
