package com.letsanjoy.xsonic.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchRequest {
    private List<String> poducers;
    private List<String> genders;
    private List<Integer> prices;
    private Boolean sortByPrice;
    private String poducer;
    private String productGender;
}
