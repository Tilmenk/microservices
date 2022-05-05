package com.tilmenk.apiGateway.config;

import com.tilmenk.apiGateway.model.rabbit.RabbitResponse;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


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
        classMapper.setIdClassMapping(Map.of("com.tilmenk.teamService.model" + ".rabbit" + ".RabbitResponse", RabbitResponse.class));
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
