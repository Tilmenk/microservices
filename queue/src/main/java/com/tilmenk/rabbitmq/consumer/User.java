package com.tilmenk.rabbitmq.consumer;


import com.tilmenk.rabbitmq.config.MessagingConfig.MessagingConfig;
import com.tilmenk.rabbitmq.dto.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class User {

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(OrderStatus orderStatus) throws InterruptedException {
        System.out.println("Message recieved from queue : " + orderStatus);
        Thread.sleep(10000);
    }
}