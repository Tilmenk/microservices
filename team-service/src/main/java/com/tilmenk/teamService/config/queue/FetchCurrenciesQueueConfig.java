package com.tilmenk.teamService.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FetchCurrenciesQueueConfig {

    public static final String QUEUE_NAME = "FETCH_CURRENCIES_Q";

    public static final String ROUTINGKEY = "FETCH_CURRENCIES_RK";

    @Bean
    public Queue fetch_currencies_q() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding fetch_currencies_binding(@Qualifier("fetch_currencies_q") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY);
    }

}
