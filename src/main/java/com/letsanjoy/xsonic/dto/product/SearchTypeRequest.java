package com.letsanjoy.xsonic.dto.product;

import com.letsanjoy.xsonic.enums.SearchProduct;
import lombok.Data;

@Data
public class SearchTypeRequest {
    private SearchProduct searchType;
    private String text;
}
