package com.example.notificationservice.service;

import com.example.notificationservice.dto.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(topics = "order-created-topic", groupId = "notification-group")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Received order-created event: " + event);
        System.out.println("Sending notification for order id: " + event.getOrderId());
    }
}