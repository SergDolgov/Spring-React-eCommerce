package com.letsanjoy.xsonic.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseUserResponse {
    private String lastName;
    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
}
