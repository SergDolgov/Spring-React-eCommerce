package com.letsanjoy.xsonic.dto.review;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static com.letsanjoy.xsonic.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;

@Data
public class ReviewRequest {

    private Long productId;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    private String author;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    private String message;

    @NotNull(message = "Choose product rating")
    private Integer rating;
}
