package com.tilmenk.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FetchPokemonQueueConfig {

    public static final String QUEUE_NAME = "FETCH_POKEMON_Q";
    public static final String ROUTINGKEY = "FETCH_POKEMON_RK";


    @Bean
    public Queue fetch_pokemon_q() {
        return new Queue(QUEUE_NAME);
    }


    @Bean
    public Binding fetch_pokemon_binding(@Qualifier("fetch_pokemon_q") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY);
    }
}
