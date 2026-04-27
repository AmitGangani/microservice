package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private String productName;
    private BigDecimal amount;

    public OrderCreatedEvent(Long id, String userId, String productName, BigDecimal amount) {
    }
}