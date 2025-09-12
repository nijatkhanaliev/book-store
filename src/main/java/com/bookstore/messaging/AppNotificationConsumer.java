package com.bookstore.messaging;

import com.bookstore.notification.SellerApplicationCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class AppNotificationConsumer {

    @RabbitListener(queues = "notification-queue")
    private void consume(SellerApplicationCreatedEvent event){
        Long userId = event.getUserId();
        String userFullName = event.getUserFullName();



    }

}
