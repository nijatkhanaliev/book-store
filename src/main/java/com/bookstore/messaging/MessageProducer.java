package com.bookstore.messaging;

import com.bookstore.notification.SellerApplicationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, SellerApplicationCreatedEvent value){
        rabbitTemplate.convertAndSend(exchange, routingKey, value);
    }

}
