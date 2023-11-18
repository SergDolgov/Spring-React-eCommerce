package com.letsanjoy.xsonic.controller;

import com.letsanjoy.xsonic.constants.ErrorMessage;
import com.letsanjoy.xsonic.dto.PasswordResetRequest;
import com.letsanjoy.xsonic.dto.auth.AuthenticationRequest;
import com.letsanjoy.xsonic.dto.auth.AuthenticationResponse;
import com.letsanjoy.xsonic.exception.ApiRequestException;
import com.letsanjoy.xsonic.mapper.AuthenticationMapper;
import com.letsanjoy.xsonic.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.letsanjoy.xsonic.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_AUTH)
public class AuthenticationController {

    private final AuthenticationMapper authenticationMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping(LOGIN)
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
    @GetMapping(FORGOT_EMAIL)
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        return ResponseEntity.ok(authenticationMapper.sendPasswordResetCode(email));
    }

    @GetMapping(RESET_CODE)
    public ResponseEntity<String> getEmailByPasswordResetCode(@PathVariable String code) {
        return ResponseEntity.ok(authenticationMapper.getEmailByPasswordResetCode(code));
    }

    @PostMapping(RESET)
    public ResponseEntity<String> passwordReset(@RequestBody PasswordResetRequest passwordReset) {
        return ResponseEntity.ok(authenticationMapper.passwordReset(passwordReset.getEmail(), passwordReset));
    }

    @PutMapping(UPDATE_PASSWORD)
    public ResponseEntity<String> updateUserPassword(@AuthenticationPrincipal UserPrincipal user,
                                                     @Valid @RequestBody PasswordResetRequest passwordReset,
                                                     BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationMapper.passwordReset(user.getEmail(), passwordReset, bindingResult));
    }

}
