package com.letsanjoy.xsonic.controller;

import com.letsanjoy.xsonic.constants.PathConstants;
import com.letsanjoy.xsonic.dto.auth.AuthenticationRequest;
import com.letsanjoy.xsonic.dto.auth.AuthenticationResponse;
import com.letsanjoy.xsonic.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_AUTH)
public class AuthenticationController {

    private final AuthenticationMapper authenticationMapper;

    @PostMapping(PathConstants.LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationMapper.login(request));
    }

}
