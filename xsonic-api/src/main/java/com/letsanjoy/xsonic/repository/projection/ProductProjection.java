package com.letsanjoy.xsonic.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getBrand();
    String getCategory();
    Integer getPrice();
    String getFilename();
    Double getRatings();



    @Value("#{target.reviews.size()}")
    Integer getReviewsCount();

    void setBrand(String brand);
    void setCategory(String category);
    void setPrice(Integer price);
}
