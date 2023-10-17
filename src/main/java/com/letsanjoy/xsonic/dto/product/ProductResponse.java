package com.letsanjoy.xsonic.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String productTitle;
    private String poducer;
    private Integer price;
    private Double productRating;
    private String filename;
    private Integer reviewsCount;
    private String volume;
}
