package com.bombi.notification.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ALERT_QUEUE = "alert_queue";

    @Bean
    public Queue alertQueue() {
        return new Queue(ALERT_QUEUE, true);
    }
}