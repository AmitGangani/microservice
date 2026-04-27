package com.example.orderservice.service;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.OrderCreatedEvent;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.UserResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository,
                        UserClient userClient,
                        OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.orderEventProducer = orderEventProducer;
    }

    @CircuitBreaker(name = "userServiceBreaker", fallbackMethod = "createOrderFallback")
    public Order createOrder(OrderRequest request) {
        UserResponse user = userClient.getUser(request.getUserId());

        if (user == null || user.getId() == null) {
            throw new RuntimeException("User not found with id: " + request.getUserId());
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .productName(request.getProductName())
                .amount(request.getAmount())
                .build();

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getProductName(),
                savedOrder.getAmount()
        );

        orderEventProducer.publishOrderCreatedEvent(event);

        return savedOrder;
    }

    public Order createOrderFallback(OrderRequest request, Throwable throwable) {
        throw new RuntimeException("User service is temporarily unavailable. Please try again later.");
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}