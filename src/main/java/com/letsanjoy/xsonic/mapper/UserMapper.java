package com.letsanjoy.xsonic.mapper;

import com.letsanjoy.xsonic.domain.User;
import com.letsanjoy.xsonic.dto.HeaderResponse;
import com.letsanjoy.xsonic.dto.product.ProductResponse;
import com.letsanjoy.xsonic.dto.user.BaseUserResponse;
import com.letsanjoy.xsonic.dto.user.UpdateUserRequest;
import com.letsanjoy.xsonic.dto.user.UserResponse;
import com.letsanjoy.xsonic.exception.InputFieldException;
import com.letsanjoy.xsonic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final CommonMapper commonMapper;
    private final UserService userService;

    public UserResponse getUserById(Long userId) {
        return commonMapper.convertToResponse(userService.getUserById(userId), UserResponse.class);
    }

    public UserResponse getUserInfo(String email) {
        return commonMapper.convertToResponse(userService.getUserInfo(email), UserResponse.class);
    }

    public List<ProductResponse> getCart(List<Long> productsIds) {
        return commonMapper.convertToResponseList(userService.getCart(productsIds), ProductResponse.class);
    }

    public HeaderResponse<BaseUserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        return commonMapper.getHeaderResponse(users.getContent(), users.getTotalPages(), users.getTotalElements(), BaseUserResponse.class);
    }

    public UserResponse updateUserInfo(String email, UpdateUserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        User user = commonMapper.convertToEntity(userRequest, User.class);
        return commonMapper.convertToResponse(userService.updateUserInfo(email, user), UserResponse.class);
    }
}
