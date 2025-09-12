package com.bookstore.messaging;

import com.bookstore.email.EmailService;
import com.bookstore.notification.SellerApplicationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "email-queue")
    private void consume(SellerApplicationCreatedEvent event) {
        Long userId = event.getUserId();
        String userFullName = event.getUserFullName();
        String message = String.format(
                "Dear Admin,\n\n" +
                        "User %s has submitted an application to create a store.\n\n" +
                        "You can review the application details here: http://localhost:8080/api/v1/users/%d\n\n" +
                        "Regards,\nBookstore Team",
                userFullName, userId
        );

        String subject = "Action Required: Approve New Bookstore Seller";

        emailService.sendEmail("admin@gmail.com", "bookstore@gmail.com", message, subject);
    }

}
