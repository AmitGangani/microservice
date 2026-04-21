package com.example.orderservice.service;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.UserResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;

    public OrderService(OrderRepository orderRepository, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    public Order createOrder(OrderRequest request) {
        UserResponse user = userClient.getUser(request.getUserId());

        if (user == null || user.getId() == null) {
            throw new RuntimeException("User not found with id " + request.getUserId());
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .productName(request.getProductName())
                .amount(request.getAmount())
                .build();

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
