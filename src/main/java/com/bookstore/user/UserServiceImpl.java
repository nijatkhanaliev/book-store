package com.bookstore.user;

import com.bookstore.notification.SellerApplicationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.bookstore.config.RabbitMQConfig.NOTIFICATION_EXCHANGE;
import static com.bookstore.security.SecurityUtil.getCurrentUser;
import static com.bookstore.user.UserStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void createBookStore() {
        User currentUser = getCurrentUser(userRepository);
        log.info("Creating book store by userId {}", currentUser.getId());
        currentUser.setUserStatus(PENDING);
        userRepository.save(currentUser);

        log.info("Creating new event for user create book store, userId {}", currentUser.getId());
        var sellerApplicationCreatedEvent = new SellerApplicationCreatedEvent(currentUser.getId(),
                currentUser.getFullName());

        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, "notification.seller",
                sellerApplicationCreatedEvent);
        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, "email.seller",
                sellerApplicationCreatedEvent);
    }

}
