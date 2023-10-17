package com.letsanjoy.xsonic.dto.user;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

import static com.letsanjoy.xsonic.constants.ErrorMessage.EMPTY_FIRST_NAME;
import static com.letsanjoy.xsonic.constants.ErrorMessage.EMPTY_LAST_NAME;

@Data
public class UpdateUserRequest {
    private Long id;

    @NotBlank(message = EMPTY_FIRST_NAME)
    private String firstName;

    @NotBlank(message = EMPTY_LAST_NAME)
    private String lastName;

    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
}
