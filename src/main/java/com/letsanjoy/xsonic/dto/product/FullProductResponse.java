package com.letsanjoy.xsonic.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FullProductResponse extends ProductResponse {
    private Integer rateCount;
    private String category;
    private String connectivity;
    private Integer finalPrice;
    private Integer originalPrice;
    private Integer quantity;
    private String description;
    private String type;
    private MultipartFile file;
}
