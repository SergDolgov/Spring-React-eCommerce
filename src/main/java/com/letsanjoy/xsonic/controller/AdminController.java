package com.letsanjoy.xsonic.controller;

import com.letsanjoy.xsonic.dto.HeaderResponse;
import com.letsanjoy.xsonic.dto.order.OrderResponse;
import com.letsanjoy.xsonic.dto.product.ProductRequest;
import com.letsanjoy.xsonic.dto.product.FullProductResponse;
import com.letsanjoy.xsonic.dto.user.BaseUserResponse;
import com.letsanjoy.xsonic.dto.user.UserResponse;
import com.letsanjoy.xsonic.mapper.OrderMapper;
import com.letsanjoy.xsonic.mapper.ProductMapper;
import com.letsanjoy.xsonic.mapper.UserMapper;
import com.letsanjoy.xsonic.constants.PathConstants;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(PathConstants.API_V1_ADMIN)
public class AdminController {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    @PostMapping(PathConstants.ADD)
    public ResponseEntity<FullProductResponse> addProduct(@RequestPart(name = "file", required = false) MultipartFile file,
                                                          @RequestPart("product") @Valid ProductRequest product,
                                                          BindingResult bindingResult) {
        return ResponseEntity.ok(productMapper.saveProduct(product, file, bindingResult));
    }

    @PostMapping(PathConstants.EDIT)
    public ResponseEntity<FullProductResponse> updateProduct(@RequestPart(name = "file", required = false) MultipartFile file,
                                                             @RequestPart("product") @Valid ProductRequest product,
                                                             BindingResult bindingResult) {
        return ResponseEntity.ok(productMapper.saveProduct(product, file, bindingResult));
    }

    @DeleteMapping(PathConstants.DELETE_BY_PRODUCT_ID)
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productMapper.deleteProduct(productId));
    }

    @GetMapping(PathConstants.ORDERS)
    public ResponseEntity<List<OrderResponse>> getAllOrders(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<OrderResponse> response = orderMapper.getAllOrders(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(PathConstants.ORDER_BY_EMAIL)
    public ResponseEntity<List<OrderResponse>> getUserOrdersByEmail(@PathVariable String userEmail, 
                                                                    @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<OrderResponse> response = orderMapper.getUserOrders(userEmail, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @DeleteMapping(PathConstants.ORDER_DELETE)
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderMapper.deleteOrder(orderId));
    }

    @GetMapping(PathConstants.USER_BY_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserById(userId));
    }

    @GetMapping(PathConstants.USER_ALL)
    public ResponseEntity<List<BaseUserResponse>> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<BaseUserResponse> response = userMapper.getAllUsers(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

}
