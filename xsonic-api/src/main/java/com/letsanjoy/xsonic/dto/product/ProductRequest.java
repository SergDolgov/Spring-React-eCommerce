package com.letsanjoy.xsonic.dto.product;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.letsanjoy.xsonic.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;

@Data
public class ProductRequest {

    private Long id;
    private String filename;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String title;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String brand;

    //@NotNull(message = FILL_IN_THE_INPUT_FIELD)
    private Integer rateCount;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String category;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String connectivity;

    //@NotNull(message = FILL_IN_THE_INPUT_FIELD)
    private Integer price;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String info;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String type;

    @NotNull(message = FILL_IN_THE_INPUT_FIELD)
    private Integer finalPrice;

    @NotNull(message = FILL_IN_THE_INPUT_FIELD)
    private Integer originalPrice;

    @NotNull(message = FILL_IN_THE_INPUT_FIELD)
    private Integer quantity;


}
