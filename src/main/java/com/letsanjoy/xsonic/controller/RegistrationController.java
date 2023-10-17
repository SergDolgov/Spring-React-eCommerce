package com.letsanjoy.xsonic.controller;

import com.letsanjoy.xsonic.dto.RegistrationRequest;
import com.letsanjoy.xsonic.mapper.AuthenticationMapper;
import com.letsanjoy.xsonic.constants.PathConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_REGISTRATION)
public class RegistrationController {

    private final AuthenticationMapper authenticationMapper;

    @PostMapping
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationRequest user, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.registerUser(user.getCaptcha(), user, bindingResult));
    }

    @GetMapping(PathConstants.ACTIVATE_CODE)
    public ResponseEntity<String> activateEmailCode(@PathVariable String code) {
        return ResponseEntity.ok(authenticationMapper.activateUser(code));
    }
}
