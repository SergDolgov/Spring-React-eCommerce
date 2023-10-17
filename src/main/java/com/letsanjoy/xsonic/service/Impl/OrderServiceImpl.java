package com.letsanjoy.xsonic.service.Impl;

import com.letsanjoy.xsonic.domain.Order;
import com.letsanjoy.xsonic.domain.OrderItem;
import com.letsanjoy.xsonic.domain.Product;
import com.letsanjoy.xsonic.exception.ApiRequestException;
import com.letsanjoy.xsonic.repository.OrderItemRepository;
import com.letsanjoy.xsonic.repository.OrderRepository;
import com.letsanjoy.xsonic.repository.ProductRepository;
import com.letsanjoy.xsonic.service.OrderService;
//import com.letsanjoy.xsonic.service.email.MailSender;

import com.letsanjoy.xsonic.constants.ErrorMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Order order = getOrderById(orderId);
        return order.getOrderItems();
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAllByOrderByIdAsc(pageable);
    }

    @Override
    public Page<Order> getUserOrders(String email, Pageable pageable) {
        return orderRepository.findOrderByEmail(email, pageable);
    }

    @Override
    @Transactional
    public Order postOrder(Order order, Map<Long, Long> productsId) {
        List<OrderItem> orderItemList = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : productsId.entrySet()) {
            Product product = productRepository.findById(entry.getKey()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount((product.getPrice() * entry.getValue()));
            orderItem.setQuantity(entry.getValue());
            orderItemList.add(orderItem);
            orderItemRepository.save(orderItem);
        }
        order.getOrderItems().addAll(orderItemList);
        orderRepository.save(order);

        String subject = "Order #" + order.getId();
        String template = "order-template";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("order", order);
        return order;
    }

    @Override
    @Transactional
    public String deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
        orderRepository.delete(order);
        return "Order deleted successfully";
    }

}
