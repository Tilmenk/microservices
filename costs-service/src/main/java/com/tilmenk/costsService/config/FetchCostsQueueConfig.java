package com.tilmenk.costsService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FetchCostsQueueConfig {

    public static final String QUEUE_NAME_POKEMON = "FETCH_COST_POKEMON_Q";
    public static final String QUEUE_NAME_TEAM = "FETCH_COST_TEAM_Q";

    public static final String ROUTINGKEY_POKEMON = "FETCH_COST_POKEMON_RK";
    public static final String ROUTINGKEY_TEAM = "FETCH_COST_TEAM_RK";

    @Bean
    public Queue fetch_cost_pokemon_q() {
        return new Queue(QUEUE_NAME_POKEMON);
    }

    @Bean
    public Queue fetch_cost_team_q() {
        return new Queue(QUEUE_NAME_TEAM);
    }

    @Bean
    public Binding fetch_cost_binding_pokemon(@Qualifier(
            "fetch_cost_pokemon_q") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_POKEMON);
    }

    @Bean
    public Binding fetch_cost_binding_team(@Qualifier("fetch_cost_team_q") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_TEAM);
    }

}
