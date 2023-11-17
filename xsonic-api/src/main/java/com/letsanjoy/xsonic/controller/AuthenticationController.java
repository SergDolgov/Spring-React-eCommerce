package com.letsanjoy.xsonic.controller;

import com.letsanjoy.xsonic.constants.ErrorMessage;
import com.letsanjoy.xsonic.constants.PathConstants;
import com.letsanjoy.xsonic.dto.auth.AuthenticationRequest;
import com.letsanjoy.xsonic.dto.auth.AuthenticationResponse;
import com.letsanjoy.xsonic.exception.ApiRequestException;
import com.letsanjoy.xsonic.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_AUTH)
public class AuthenticationController {

    private final AuthenticationMapper authenticationMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping(PathConstants.LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            return ResponseEntity.ok(authenticationMapper.login(request));

        } catch (InternalAuthenticationServiceException e) {
            throw new ApiRequestException(ErrorMessage.EMAIL_NOT_ACTIVATED, HttpStatus.FORBIDDEN);
        } catch (AuthenticationException e) {
            throw new ApiRequestException(ErrorMessage.INCORRECT_PASSWORD, HttpStatus.FORBIDDEN);
        }

    }

}
