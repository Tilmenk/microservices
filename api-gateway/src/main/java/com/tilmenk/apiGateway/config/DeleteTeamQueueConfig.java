package com.tilmenk.apiGateway.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DeleteTeamQueueConfig {

    public static final String QUEUE_NAME = "DELETE_TEAM_Q";
    public static final String ROUTINGKEY = "DELETE_TEAM_RK";

    @Bean
    public Queue delete_team_q() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding delete_team_binding(@Qualifier("delete_team_q") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY);
    }

}
