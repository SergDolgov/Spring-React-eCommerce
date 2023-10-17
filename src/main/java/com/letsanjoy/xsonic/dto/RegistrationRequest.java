package com.letsanjoy.xsonic.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.letsanjoy.xsonic.constants.ErrorMessage.*;

@Data
public class RegistrationRequest {

    @NotBlank(message = "Fill captcha.")
    private String captcha;

    @NotBlank(message = EMPTY_FIRST_NAME)
    private String firstName;

    @NotBlank(message = EMPTY_LAST_NAME)
    private String lastName;

    @Size(min = 6, max = 16, message = PASSWORD_CHARACTER_LENGTH)
    private String password;

    @Size(min = 6, max = 16, message = PASSWORD2_CHARACTER_LENGTH)
    private String password2;

    @Email(message = INCORRECT_EMAIL)
    @NotBlank(message = EMAIL_CANNOT_BE_EMPTY)
    private String email;
}
