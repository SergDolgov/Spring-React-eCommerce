package com.letsanjoy.xsonic.dto;

import lombok.Data;

import jakarta.validation.constraints.Size;

import static com.letsanjoy.xsonic.constants.ErrorMessage.PASSWORD2_CHARACTER_LENGTH;
import static com.letsanjoy.xsonic.constants.ErrorMessage.PASSWORD_CHARACTER_LENGTH;

@Data
public class PasswordResetRequest {

    private String email;

    @Size(min = 6, max = 16, message = PASSWORD_CHARACTER_LENGTH)
    private String password;

    @Size(min = 6, max = 16, message = PASSWORD2_CHARACTER_LENGTH)
    private String password2;
}
