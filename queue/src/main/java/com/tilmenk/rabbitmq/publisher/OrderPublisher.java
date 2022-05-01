package com.tilmenk.rabbitmq.publisher;


import com.tilmenk.rabbitmq.dto.Order;
import com.tilmenk.rabbitmq.dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.tilmenk.rabbitmq.config.MessagingConfig.MessagingConfig.EXCHANGE;
import static com.tilmenk.rabbitmq.config.MessagingConfig.MessagingConfig.ROUTING_KEY;

@RestController
@RequestMapping("/order")
public class OrderPublisher {
    @Autowired
    private RabbitTemplate template;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order,
                            @PathVariable String restaurantName) {
        order.setOrderId(UUID.randomUUID().toString());
        //restaurantService
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order " +
                "placed successfully in " + restaurantName);
        template.convertAndSend(EXCHANGE, ROUTING_KEY, orderStatus);
        return "Success";
    }


}
