package com.tilmenk.teamService.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CreateTeamQueueConfig {

    public static final String QUEUE_NAME = "CREATE_TEAM_Q";
    public static final String ROUTINGKEY = "CREATE_TEAM_RK";

    @Bean
    public Queue create_team_q() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding create_team_binding(@Qualifier("create_team_q") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY);
    }

}
