package com.letsanjoy.xsonic.controller;

import com.letsanjoy.xsonic.dto.HeaderResponse;
import com.letsanjoy.xsonic.dto.order.OrderItemResponse;
import com.letsanjoy.xsonic.dto.order.OrderRequest;
import com.letsanjoy.xsonic.dto.order.OrderResponse;
import com.letsanjoy.xsonic.mapper.OrderMapper;
import com.letsanjoy.xsonic.security.UserPrincipal;
import com.letsanjoy.xsonic.constants.PathConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_ORDER)
public class OrderController {

    private final OrderMapper orderMapper;

    @GetMapping(PathConstants.ORDER_ID)
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderMapper.getOrderById(orderId));
    }

    @GetMapping(PathConstants.ORDER_ID_ITEMS)
    public ResponseEntity<List<OrderItemResponse>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderMapper.getOrderItemsByOrderId(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(@AuthenticationPrincipal UserPrincipal user,
                                                             @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<OrderResponse> response = orderMapper.getUserOrders(user.getEmail(), pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> postOrder(@Valid @RequestBody OrderRequest order, BindingResult bindingResult) {
        return ResponseEntity.ok(orderMapper.postOrder(order, bindingResult));
    }

}
