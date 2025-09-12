package com.bookstore.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EMAIL_QUEUE = "email-queue";
    public static final String NOTIFICATION_QUEUE = "notification-queue";
    public static final String NOTIFICATION_EXCHANGE = "notification-exchange";
    public static final String EMAIL_ROUTING_KEY = "email.*";
    public static final String NOTIFICATION_ROUTING_KEY = "notification.*";

    @Bean
    public Queue emailQueue(){
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public Queue notificationQueue(){
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding bindingNotification(Queue notificationQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(notificationQueue)
                .to(topicExchange)
                .with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Binding bindingEmail(Queue emailQueue, TopicExchange topicExchange){
        return BindingBuilder.bind(emailQueue)
                .to(topicExchange)
                .with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

}
