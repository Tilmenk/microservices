package com.tilmenk.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FetchPriceQueueConfig {

    public static final String QUEUE_NAME = "FETCH_PRICE_Q";

    public static final String ROUTINGKEY = "FETCH_PRICE_RK";

    @Bean
    public Queue fetch_price_q() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding fetch_price_binding(@Qualifier("fetch_price_q") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY);
    }

}
