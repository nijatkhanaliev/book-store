package com.bookstore.messaging;

import com.bookstore.notification.SellerApplicationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppNotificationConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "notification-queue")
    private void consume(SellerApplicationCreatedEvent event){
//        Long userId = event.getUserId();
//        String userFullName = event.getUserFullName();
        messagingTemplate.convertAndSend("topic/admin-notifications", "new Notification for Admin");
    }

}
