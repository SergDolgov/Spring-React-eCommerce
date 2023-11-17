package com.letsanjoy.xsonic.controller;

import com.letsanjoy.xsonic.dto.product.ProductResponse;
import com.letsanjoy.xsonic.dto.user.UpdateUserRequest;
import com.letsanjoy.xsonic.dto.user.UserResponse;
import com.letsanjoy.xsonic.mapper.UserMapper;
import com.letsanjoy.xsonic.security.UserPrincipal;
import com.letsanjoy.xsonic.constants.PathConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.letsanjoy.xsonic.constants.PathConstants.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_USERS)
public class UserController {

    private final UserMapper userMapper;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public ResponseEntity<UserResponse> getUserInfo(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(userMapper.getUserInfo(user.getEmail()));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping
    public ResponseEntity<UserResponse> updateUserInfo(@AuthenticationPrincipal UserPrincipal user,
                                                       @Valid @RequestBody UpdateUserRequest request,
                                                       BindingResult bindingResult) {
        return ResponseEntity.ok(userMapper.updateUserInfo(user.getEmail(), request, bindingResult));
    }

    @PostMapping(PathConstants.CART)
    public ResponseEntity<List<ProductResponse>> getCart(@RequestBody List<Long> productsIds) {
        return ResponseEntity.ok(userMapper.getCart(productsIds));
    }

}
