package com.letsanjoy.xsonic.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface FullProductProjection extends ProductProjection{
    @Override
    Long getId();

    @Override
    String getTitle();

    @Override
    String getBrand();

    @Override
    Integer getPrice();

    @Override
    String getFilename();

    @Override
    Double getRatings();

    @Override
    Integer getReviewsCount();

    @Override
    void setBrand(String brand);

    @Override
    void setConnectivity(String connectivity);

    @Override
    void setPrice(Integer price);

    String getInfo();

    String getCategory();

    String getType();

    String getConnectivity();

    String getFinalPrice();

    String getOriginalPrice();

    String getQuantity();

    Integer getRateCount();


}
