package com.letsanjoy.xsonic.dto.auth;

import com.letsanjoy.xsonic.dto.user.UserResponse;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private UserResponse user;
    private String token;
}
