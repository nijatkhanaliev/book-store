package com.bookstore.user;

import com.bookstore.messaging.MessageProducer;
import com.bookstore.notification.Notification;
import com.bookstore.notification.NotificationRepository;
import com.bookstore.notification.SellerApplicationCreatedEvent;
import com.bookstore.security.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bookstore.config.RabbitMQConfig.NOTIFICATION_EXCHANGE;
import static com.bookstore.security.SecurityUtil.getCurrentUser;
import static com.bookstore.user.UserStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;
    private final MessageProducer messageProducer;

    @Override
    @Transactional
    public void createBookStore() {
        User currentUser = getCurrentUser(userRepository);
        log.info("Creating book store by userId {}", currentUser.getId());
        currentUser.setUserStatus(PENDING);
        userRepository.save(currentUser);

        log.info("Creating new event for user create book store, userId {}", currentUser.getId());
        var sellerApplicationCreatedEvent = new SellerApplicationCreatedEvent(currentUser.getId(),
                currentUser.getFullName());

        User admin = SecurityUtil.findAdmin(userRepository);

        log.info("Creating new Notification for admin, adminId {}", admin.getId());
        Notification notification = new Notification();
        notification.setMessage("Approve new Book seller");
        notification.setRecipientId(admin.getId());
        notificationRepository.save(notification);

        messageProducer.send(NOTIFICATION_EXCHANGE, "notification.seller",
                sellerApplicationCreatedEvent);
        messageProducer.send(NOTIFICATION_EXCHANGE, "email.seller",
                sellerApplicationCreatedEvent);
    }

}
