package com.letsanjoy.xsonic.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FullProductResponse extends ProductResponse {
    private Integer year;
    private String country;
    private String productGender;
    private String fragranceTopNotes;
    private String fragranceMiddleNotes;
    private String fragranceBaseNotes;
    private String description;
    private String type;
    private MultipartFile file;
}
