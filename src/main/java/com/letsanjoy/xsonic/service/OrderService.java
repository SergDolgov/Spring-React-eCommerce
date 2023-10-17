package com.letsanjoy.xsonic.service;

import com.letsanjoy.xsonic.domain.Order;
import com.letsanjoy.xsonic.domain.OrderItem;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Order getOrderById(Long orderId);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    
    Page<Order> getAllOrders(Pageable pageable);

    Page<Order> getUserOrders(String email, Pageable pageable);

    Order postOrder(Order validOrder, Map<Long, Long> productsId);

    String deleteOrder(Long orderId);

}
