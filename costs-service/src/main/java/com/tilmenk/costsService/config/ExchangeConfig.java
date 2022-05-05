package com.tilmenk.costsService.config;

import com.tilmenk.costsService.model.Pokemon;
import com.tilmenk.costsService.model.Team;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
public class ExchangeConfig {

    public static final String EXCHANGE_NAME = "DEFAULT_EXCHANGE";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter converter() {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter =
                new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(classMapper());
        return jackson2JsonMessageConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        HashMap<String, Class<?>> knownClasses = new HashMap<>();
        knownClasses.put("com.tilmenk.apiGateway.model.teamService.Team",
                Team.class);
        knownClasses.put("com.tilmenk.teamService.model.Pokemon",
                Pokemon.class);
        classMapper.setIdClassMapping(knownClasses);
        classMapper.setTrustedPackages("*");
        return classMapper;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate =
                new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        rabbitTemplate.setReceiveTimeout(10000);
        return rabbitTemplate;
    }
}
