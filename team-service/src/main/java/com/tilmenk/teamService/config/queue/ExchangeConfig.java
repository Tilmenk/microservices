package com.tilmenk.teamService.config.queue;

import com.tilmenk.teamService.model.Currencies;
import com.tilmenk.teamService.model.Team;
import com.tilmenk.teamService.model.TeamWithPokemonNames;
import com.tilmenk.teamService.model.apiGateway.DeleteTeamData;
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
        knownClasses.put("com.tilmenk.currencyService" + ".model.Currencies",
                Currencies.class);
        knownClasses.put("com.tilmenk.apiGateway" + ".model.teamService.Team"
                , Team.class);
        knownClasses.put("com.tilmenk.apiGateway.model.teamService" +
                ".TeamWithPokemonNames", TeamWithPokemonNames.class);
        knownClasses.put("com.tilmenk.apiGateway.model.teamService" +
                ".DeleteTeamData", DeleteTeamData.class);
        classMapper.setIdClassMapping(knownClasses);
        classMapper.setTrustedPackages("*");
        return classMapper;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate =
                new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        rabbitTemplate.setReceiveTimeout(500);
        rabbitTemplate.setReplyTimeout(500);
        return rabbitTemplate;
    }
}
