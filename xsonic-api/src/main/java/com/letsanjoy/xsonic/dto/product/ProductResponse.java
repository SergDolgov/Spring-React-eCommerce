package com.letsanjoy.xsonic.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String title;
    private String brand;
    private Integer price;
    private Double ratings;
    private String filename;
    private Integer reviewsCount;
    private String info;
}
